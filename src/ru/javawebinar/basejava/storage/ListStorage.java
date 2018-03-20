package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    public void deleteInternal(Integer i) {
        list.remove(i.intValue());
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

    @Override
    protected Integer find(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getByIndex(Integer index) {
        return list.get(index);
    }

    @Override
    protected boolean checkIndex(Integer index) {
        return index > -1;
    }

    @Override
    protected void saveInternal(Resume r, Integer integer) {
        list.add(r);
    }
}
