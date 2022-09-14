package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String path;
    private final Gson mapper = new Gson();

    public FileSerializer(String fileName) {
        path = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (var writer = new FileWriter(path)) {
            mapper.toJson(data, writer);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
