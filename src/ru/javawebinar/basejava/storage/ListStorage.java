package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();
    private int size = 0;

    @Override
    public void clear() {
        list.clear();
        size = 0;
    }

    @Override
    public Resume get(String uuid) {
        for (Resume r : list) {
            if (r.getUuid().equals(uuid))
                return r;
        }
        throw new NotFoundException(uuid);
    }

    @Override
    public void save(Resume r) {
        for (Resume re : list) {
            if (re.getUuid().equals(r.getUuid()))
                throw new AlreadyExistsException(r.getUuid());
        }
        list.add(r);
        size++;
    }

    @Override
    public void delete(String uuid) {
        if (!list.removeIf((r) -> r.getUuid().equals(uuid)))
            throw new NotFoundException(uuid);
        size--;
    }

    @Override
    public void update(Resume r) {
        for (Resume re : list) {
            if (re.getUuid().equals(r.getUuid())) {
                
            }
        }
    }

    @Override
    public Resume[] getAll() {
        Resume[] res = new Resume[list.size()];
        return list.toArray(res);
    }

    @Override
    public int size() {
        return size;
    }
}
