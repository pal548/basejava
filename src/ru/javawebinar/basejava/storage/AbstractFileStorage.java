package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;
    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if(!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if(!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
    }

    @Override
    public int size() {
        String[] strings = directory.list();
        if (strings == null) {
            return 0;
        } else {
            return strings.length;
        }
    }

    @Override
    protected File find(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getByIndex(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected boolean checkIndex(File file) {
        return file.exists();
    }

    @Override
    protected void saveInternal(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected void deleteInternal(File file) {
        file.delete();
    }

    @Override
    protected void updateInternal(File file, Resume r) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getAllList() {
        File[] files = directory.listFiles();
        if (files == null) {
            return Collections.emptyList();
        } else {
            List<Resume> result = new ArrayList<>();
            for (File f : files){
                try {
                    result.add(doRead(f));
                } catch (IOException e) {
                    throw new StorageException("IO error", f.getName(), e);
                }
            }
            return result;
        }
    }
}