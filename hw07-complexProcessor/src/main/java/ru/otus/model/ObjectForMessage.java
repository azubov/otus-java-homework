package ru.otus.model;

import java.util.List;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    protected ObjectForMessage clone() {
        var instance = new ObjectForMessage();
        var dataCopy = List.copyOf(this.getData());
        instance.setData(dataCopy);
        return instance;
    }
}
