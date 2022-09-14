package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final String path;
    private final Gson mapper = new Gson();

    public ResourcesFileLoader(String fileName) {
        path = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var reader = new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)))) {
            var toType = TypeToken.getParameterized(List.class, Measurement.class).getType();
            return mapper.fromJson(reader, toType);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
