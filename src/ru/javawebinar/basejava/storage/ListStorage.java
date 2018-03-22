package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected boolean checkIndex(Integer index) {
        return index > -1;
    }

    @Override
    protected Resume getByIndex(Integer index) {
        return list.get(index);
    }

    @Override
    protected void saveInternal(Resume r, Integer integer) {
        list.add(r);
    }

    @Override
    public void deleteInternal(Integer i) {
        list.remove(i.intValue());
    }

    @Override
    public void updateInternal(Integer i, Resume r) {
        list.set(i, r);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>(list);
        sortedList.sort(Resume::compareTo);
        return sortedList;
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

}
