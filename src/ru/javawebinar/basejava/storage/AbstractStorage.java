package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<I> implements Storage {

    @Override
    public abstract void clear();

    @Override
    public Resume get(String uuid) {
        I index = find(uuid);
        if (checkIndex(index))
            return getByIndex(index);
        else
            throw new NotFoundException(uuid);
    }

    @Override
    public void save(Resume r) {
        I i = find(r.getUuid());
        if (checkIndex(i)) {
            throw new ExistsException(r.getUuid());
        } else {
            saveInternal(r, i);
        }
    }

    @Override
    public void delete(String uuid) {
        I i = find(uuid);
        if (checkIndex(i)) {
            deleteInternal(i);
        } else {
            throw new NotFoundException(uuid);
        }
    }

    @Override
    public void update(Resume r) {
        I i = find(r.getUuid());
        if (!checkIndex(i)) {
            throw new NotFoundException(r.getUuid());
        } else {
            updateInternal(i, r);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getAllList();
        sortedList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return sortedList;
    }

    @Override
    public abstract int size();

    protected abstract I find(String uuid);

    protected abstract Resume getByIndex(I index);

    protected abstract boolean checkIndex(I index);

    protected abstract void saveInternal(Resume r, I i);

    protected abstract void deleteInternal(I i);

    protected abstract void updateInternal(I i, Resume r);

    protected abstract List<Resume> getAllList();
}
