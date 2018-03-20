package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

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
    public void update(Resume r) {
        Iterator<Map.Entry<String, Resume>> iterator = map.entrySet().iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Map.Entry<String, Resume> entry = iterator.next();
            if (entry.getValue().getUuid().equals(r.getUuid())) {
                iterator.remove();
                found = true;
                break;
            }
        }
        if (found)
            map.put(r.getUuid(),r);
        else
            throw new NotFoundException(r.getUuid());
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
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
