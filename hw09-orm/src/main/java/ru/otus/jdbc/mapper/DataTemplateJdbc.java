package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<?> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<?> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), this::getFirstInstance);
    }

    @Override
    public List<T> findAll(Connection connection) {
         return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), this::getAllInstances)
                 .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getParams(client));
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getParams(client));
    }

    private T getFirstInstance(ResultSet rs) {
        try {
            if (rs.next()) {
                return createInstance(rs);
            }
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
        return null;
    }

    private T createInstance(ResultSet rs) throws Exception {
        var obj = entityClassMetaData.getConstructor().newInstance();
        for (var field : entityClassMetaData.getAllFields()) {
            field.setAccessible(true);
            var value = rs.getObject(field.getName());
            field.set(obj, value);
        }
        return (T) obj;
    }

    private List<T> getAllInstances(ResultSet rs) {
        try {
            var instancesList = new ArrayList<T>();
            while (rs.next()) {
                instancesList.add(createInstance(rs));
            }
            return instancesList;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getParams(T client) {
        try {
            var params = new ArrayList<>();
            for (var field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                var value = field.get(client);
                params.add(value);
            }
            return params;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
