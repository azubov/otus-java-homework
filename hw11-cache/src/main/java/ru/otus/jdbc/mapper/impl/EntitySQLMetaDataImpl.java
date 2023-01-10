package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private static final String SELECT_ALL = "select * from %s";
    private static final String SELECT_ALL_BY_ID = "select * from %s where %s = ?";
    private static final String INSERT = "insert into %s (%s) values (%s)";
    private static final String UPDATE = "update %s set %s where ID = %s";

    private static final String INSERT_VALUE = "?";
    private static final String UPDATE_FIELD = "%s = ?";

    private final String className;
    private final String idFieldName;
    private final List<Field> fieldsWithoutId;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        className = entityClassMetaData.getName();
        idFieldName = entityClassMetaData.getIdField().getName();
        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
    }

    @Override
    public String getSelectAllSql() {
        return SELECT_ALL.formatted(className);
    }

    @Override
    public String getSelectByIdSql() {
        return SELECT_ALL_BY_ID.formatted(className, idFieldName);
    }

    @Override
    public String getInsertSql() {
        return INSERT.formatted(className, getInsertFields(), getInsertValues());
    }

    @Override
    public String getUpdateSql() {
        return UPDATE.formatted(className, getUpdateFields(), idFieldName);
    }

    private String getInsertFields() {
        return fieldsWithoutId.stream().map(Field::getName).collect(Collectors.joining(","));
    }

    private String getInsertValues() {
        return fieldsWithoutId.stream().map(field -> INSERT_VALUE).collect(Collectors.joining(","));
    }

    private String getUpdateFields() {
        return fieldsWithoutId.stream()
                .map(field -> UPDATE_FIELD.formatted(field.getName())).collect(Collectors.joining(","));
    }
}

