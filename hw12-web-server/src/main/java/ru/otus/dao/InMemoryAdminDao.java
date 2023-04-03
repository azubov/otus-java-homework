package ru.otus.dao;

import ru.otus.helpers.HashHelper;
import ru.otus.model.Admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAdminDao implements AdminDao {

    private final Map<Long, Admin> users;

    public InMemoryAdminDao() {
        users = new HashMap<>();
        users.put(1L, new Admin(1L, "Крис Гир", "admin", HashHelper.hash("admin")));
    }

    @Override
    public Optional<Admin> findByLogin(String login) {
        return users.values().stream().filter(v -> v.login().equals(login)).findFirst();
    }
}
