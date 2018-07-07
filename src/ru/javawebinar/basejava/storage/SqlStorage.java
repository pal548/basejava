package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execSQL("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExec(con -> {
            Resume r;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * " +
                            "FROM resume r " +
                            "     LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                            "WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotFoundException(uuid);
                }
                r = getResumeFromResultSet(rs);
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * " +
                            "FROM section s " +
                            "WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    while (!rs.isAfterLast()) {
                        getSection(rs, r);
                        rs.next();
                    }
                }
            }
            return r;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExec(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.executeUpdate();
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistsException(r.getUuid(), e);
                } else {
                    throw e;
                }
            }
            saveContacts(r.getUuid(), r.getContacts(), con);
            saveSections(con, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execSQL("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotFoundException(uuid);
            }
            return null;
        });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExec(con -> {
            try (PreparedStatement ps = con.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotFoundException(r.getUuid());
                }
            }
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            saveContacts(r.getUuid(), r.getContacts(), con);
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM section s WHERE s.resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            saveSections(con, r);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes_map = new LinkedHashMap<>();
        return sqlHelper.transactionalExec(con -> {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * " +
                            "FROM resume r " +
                            "ORDER BY r.full_name, r.uuid")) {
                ResultSet rs = ps.executeQuery();
                rs.next();
                while (!rs.isAfterLast()) {
                    String uuid = rs.getString("uuid");
                    resumes_map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                    rs.next();
                }
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * " +
                            "FROM contact " +
                            "ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return null;
                } else {
                    String uuid = null;
                    Resume r = null;
                    while (!rs.isAfterLast()) {
                        if (!rs.getString("resume_uuid").equals(uuid)) {
                            uuid = rs.getString("resume_uuid");
                            r = resumes_map.get(uuid);
                        }
                        r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        rs.next();
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * " +
                            "FROM section " +
                            "ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return null;
                } else {
                    String uuid = null;
                    Resume r = null;
                    while (!rs.isAfterLast()) {
                        if (!rs.getString("resume_uuid").equals(uuid)) {
                            uuid = rs.getString("resume_uuid");
                            r = resumes_map.get(uuid);
                        }
                        getSection(rs, r);
                        rs.next();
                    }
                }
            }
            return new ArrayList<>(resumes_map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execSQL("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private Resume getResumeFromResultSet(ResultSet rs) throws SQLException {
        String uuid = rs.getString("uuid");
        Resume r = new Resume(uuid, rs.getString("full_name"));
        if (rs.getString("type") != null) {
            do {
                r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            } while (rs.next() && rs.getString("uuid").equals(uuid));
        } else {
            rs.next();
        }
        return r;
    }

    private void saveContacts(String uuid, Map<ContactType, String> contacts, Connection con) throws
            SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            if (contacts.size() > 0) {
                for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
                    ps.setString(1, uuid);
                    ps.setString(2, e.getKey().toString());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void saveSections(Connection con, Resume r) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            if (r.getSections().size() > 0) {
                for (Map.Entry<SectionType, AbstractSectionData> e : r.getSections().entrySet()) {
                    SectionType type = e.getKey();
                    ps.setString(1, r.getUuid());
                    ps.setString(2, type.toString());
                    String s = null;
                    switch (type) {
                        case EDUCATION:
                        case PERSONAL:
                        case OBJECTIVE:
                            s = ((SectionSingle) e.getValue()).getValue();
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            s = String.join("\n", ((SectionMultiple) e.getValue()).getStrings());
                            break;
                    }
                    ps.setString(3, s);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void getSection(ResultSet rs, Resume r) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case EDUCATION:
            case PERSONAL:
            case OBJECTIVE:
                r.addSection(type, new SectionSingle(rs.getString("value")));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                SectionMultiple sec = new SectionMultiple();
                String[] strs = rs.getString("value").split("\n");
                for (String s : strs) {
                    sec.addText(s);
                }
                r.addSection(type, sec);
                break;
        }
    }
}
