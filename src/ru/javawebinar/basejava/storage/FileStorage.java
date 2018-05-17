package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private ResumeSerializer resumeSerializer;

    public FileStorage(String dir, ResumeSerializer resumeSerializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = new File(dir);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        Objects.requireNonNull(resumeSerializer, "resume serializer must not be null");
        this.resumeSerializer = resumeSerializer;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", "");
        } else {
            for (File f : files) {
                deleteInternal(f);
            }
        }
    }

    @Override
    public int size() {
        String[] strings = directory.list();
        if (strings == null) {
            throw new StorageException("IO error", "");
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
            return resumeSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean checkIndex(File file) {
        return file.exists();
    }

    @Override
    protected void saveInternal(Resume r, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("IO error", file.getName());
            }
            updateInternal(file, r);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteInternal(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    protected void updateInternal(File file, Resume r) {
        try {
            resumeSerializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getAllList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", "");
        } else {
            List<Resume> result = new ArrayList<>();
            for (File f : files) {
                result.add(getByIndex(f));
            }
            return result;
        }
    }
}
