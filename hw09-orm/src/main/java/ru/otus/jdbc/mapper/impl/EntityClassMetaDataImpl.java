package ru.otus.jdbc.mapper.impl;

import ru.otus.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final List<Field> allFields;
    private final Field idField;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.name = clazz.getSimpleName();
        this.constructor = getConstructor(clazz);
        this.allFields = List.of(clazz.getDeclaredFields());
        this.idField = getAnnotatedField(allFields, Id.class);
        this.fieldsWithoutId = getNotAnnotatedFields(allFields, Id.class);
    }

    private Constructor<T> getConstructor(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getAnnotatedField(List<Field> fields, Class<? extends Annotation> annotation) {
        return fields.stream().filter(field -> field.isAnnotationPresent(annotation)).findFirst().orElseThrow();
    }

    private List<Field> getNotAnnotatedFields(List<Field> fields, Class<? extends Annotation> annotation) {
        return fields.stream().filter(field -> !field.isAnnotationPresent(annotation)).toList();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
