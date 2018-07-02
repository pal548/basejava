package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.RuntimeSQLException;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return sqlHelper.execSQL(
                "SELECT * " +
                "FROM resume r " +
                "     LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            if (rs.getString("type") != null) {
                do {
                    r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                } while (rs.next());
            }
            return r;
        });
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.transactionalExec(con -> {
                try(PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")){
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.executeUpdate();
                } catch (SQLException e){
                    if (e.getSQLState().equals("23505")) {
                        throw new ExistsException(r.getUuid(), e);
                    } else {
                        throw e;
                    }
                }
                try(PreparedStatement ps = con.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                    if (r.getContacts().size() > 0) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, e.getKey().toString());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                }
                return null;
            });
        } catch (RuntimeSQLException e) {
            if (((SQLException)e.getCause()).getSQLState().equals("23505")) {
                throw new ExistsException(r.getUuid(), e);
            } else {
                throw e;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execSQL("DELETE FROM resume where uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotFoundException(uuid);
            }
            return null;
        });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.execSQL("UPDATE resume set full_name = ? where uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotFoundException(r.getUuid());
            }
            return null;
        });

    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        sqlHelper.execSQL("SELECT * FROM resume order by full_name", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        return sqlHelper.execSQL("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
