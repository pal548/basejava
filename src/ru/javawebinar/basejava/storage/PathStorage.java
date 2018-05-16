package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private ResumeSerializer resumeSerializer;

    protected PathStorage(String dir, ResumeSerializer resumeSerializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        Objects.requireNonNull(resumeSerializer, "resume serializer must not be null");
        this.resumeSerializer = resumeSerializer;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteInternal);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    protected Path find(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected Resume getByIndex(Path path) {
        try {
            return resumeSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }



    @Override
    protected boolean checkIndex(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void saveInternal(Resume r, Path path) {
        try {
            Files.createFile(path);
            updateInternal(path, r);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }



    @Override
    protected void deleteInternal(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateInternal(Path path, Resume r) {
        try {
            resumeSerializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getAllList() {
        try {
            List<Resume> result = new ArrayList<>();
            Files.list(directory).forEach((p) -> result.add(getByIndex(p)));
            return result;
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }
}
