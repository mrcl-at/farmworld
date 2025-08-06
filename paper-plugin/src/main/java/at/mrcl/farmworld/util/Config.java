package at.mrcl.farmworld.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class Config {

    public static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static <T extends Config> T read(File file, Class<T> clazz) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File (" + file.getPath() + ") not found");
        }
        return gson.fromJson(Files.readString(file.toPath()), clazz);
    }

    public void save(File file) throws IOException {
        if (!file.exists()) file.createNewFile();
        Files.writeString(file.toPath(), gson.toJson(this));
    }
}
