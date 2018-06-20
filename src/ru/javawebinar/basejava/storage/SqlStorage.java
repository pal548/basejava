package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.RuntimeSQLException;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    public void closeConnection() {
        sqlHelper.close();
    }

    @Override
    public void clear() {
        sqlHelper.execSQL("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        final Resume[] r = new Resume[1];
        sqlHelper.execSQL("SELECT * FROM resume r where r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(uuid);
            }
            r[0] = new Resume(uuid, rs.getString("full_name"));
        });
        return r[0];
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.execSQL("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            });
        } catch (RuntimeSQLException e) {
            throw new AlreadyExistsException(r.getUuid(), e);
        }
    }

    @Override
    public void delete(String uuid) {
        get(uuid);
        sqlHelper.execSQL("DELETE FROM resume where uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.execSQL("UPDATE resume set full_name = ? where uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
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
        });
        return list;
    }

    @Override
    public int size() {
        final int[] result = new int[1];
        sqlHelper.execSQL("SELECT count(*) FROM resume", (ps) -> {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result[0] = rs.getInt(1);
            }
        });
        return result[0];
    }
}
