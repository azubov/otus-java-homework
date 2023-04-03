package ru.otus.services;

import ru.otus.dao.AdminDao;
import ru.otus.helpers.HashHelper;

public class AuthServiceImpl implements AuthService {

    private final AdminDao dao;

    public AuthServiceImpl(AdminDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dao.findByLogin(login)
                .map(user -> user.password().equals(HashHelper.hash(password)))
                .orElse(false);
    }

}
