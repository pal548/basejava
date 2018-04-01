package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapIntStorage extends AbstractStorage<Integer> {
    private Map<Integer, Resume> map = new HashMap<>();

    private int nextId = 1;

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected Integer find(String uuid) {
        for(Map.Entry<Integer, Resume> e : map.entrySet()) {
            if (Objects.equals(e.getValue().getUuid(), uuid)) {
                return e.getKey();
            }
        }
        return null;
    }

    @Override
    protected Resume getByIndex(Integer index) {
        return map.get(index);
    }

    @Override
    protected boolean checkIndex(Integer index) {
        return index != null;
    }

    @Override
    protected void saveInternal(Resume r, Integer integer) {
        map.put(nextId++, r);
    }

    @Override
    protected void deleteInternal(Integer index) {
        map.remove(index);
    }

    @Override
    protected void updateInternal(Integer index, Resume r) {
        map.replace(index, r);
    }

    @Override
    protected List<Resume> getAllList() {
        return new ArrayList<>(map.values());
    }
}
