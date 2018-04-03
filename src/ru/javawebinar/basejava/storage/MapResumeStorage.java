package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected Resume find(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean checkIndex(Resume index) {
        return index != null;
    }

    @Override
    protected Resume getByIndex(Resume index) {
        return index;
    }

    @Override
    protected void saveInternal(Resume r, Resume index) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void deleteInternal(Resume i) {
        map.remove(i.getUuid());
    }

    @Override
    public void updateInternal(Resume key, Resume r) {
        map.replace(key.getUuid(), r);
    }

    @Override
    protected List<Resume> getAllList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
