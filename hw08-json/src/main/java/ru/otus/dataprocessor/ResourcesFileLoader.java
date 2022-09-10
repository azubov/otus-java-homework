package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String path;
    private final Gson mapper = new Gson();

    public ResourcesFileLoader(String fileName) {
        path = ClassLoader.getSystemResource(fileName).getPath();
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        var toType = TypeToken.getParameterized(List.class, Measurement.class).getType();
        return mapper.fromJson(new FileReader(path), toType);
    }
}
