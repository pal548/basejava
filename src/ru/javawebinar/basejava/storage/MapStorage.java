package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected boolean checkIndex(String index) {
        return index != null;
    }

    @Override
    protected Resume getByIndex(String index) {
        return map.get(index);
    }

    @Override
    protected void saveInternal(Resume r, String s) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void deleteInternal(String i) {
        map.remove(i);
    }

    @Override
    public void updateInternal(String key, Resume r) {
        map.replace(key, r);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(map.values());
        list.sort(Resume::compareByFullName);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected String find(String uuid) {
        for (String key : map.keySet()){
            if(Objects.equals(key, uuid))
                return key;
        }
        return null;
    }


}
