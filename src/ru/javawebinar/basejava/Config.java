package ru.javawebinar.basejava;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();
    private static final Config INSTANCE = new Config();
    private static final String PROPS_FILENAME = "D:\\javaops\\basejava\\config\\resumes.properties";
    private static String storageDir;
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;


    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Files.newInputStream(Paths.get(PROPS_FILENAME))) {
            props.load(is);

            storageDir = props.getProperty("storage.dir");

            Path dir = Paths.get(storageDir);
            if (!Files.isDirectory(dir) || !Files.isWritable(dir)) {
                throw new IllegalStateException(storageDir + " is not directory or is not writable");
            }

            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS_FILENAME);
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
