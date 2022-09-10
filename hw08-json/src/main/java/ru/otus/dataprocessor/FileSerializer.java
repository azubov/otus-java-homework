package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String path;
    private final Gson mapper = new Gson();

    public FileSerializer(String fileName) {
        path = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        //формирует результирующий json и сохраняет его в файл
        try (var writer = new FileWriter(path)) {
            mapper.toJson(data, writer);
        }
    }
}
