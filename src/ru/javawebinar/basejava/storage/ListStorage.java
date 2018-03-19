package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
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
    }

    @Override
    public void delete(String uuid) {
        if (!list.removeIf((r) -> r.getUuid().equals(uuid)))
            throw new NotFoundException(uuid);
    }

    @Override
    public void update(Resume r) {
        Iterator<Resume> iterator = list.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Resume ri = iterator.next();
            if (ri.getUuid().equals(r.getUuid())) {
                iterator.remove();
                found = true;
                break;
            }
        }
        if (found)
            list.add(r);
        else
            throw new NotFoundException(r.getUuid());
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
