package ru.otus.config;

import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;

public class MigrationsConfig {

    public static void create(String url, String user, String pass) {
        new MigrationsExecutorFlyway(url, user, pass).executeMigrations();
    }
}
