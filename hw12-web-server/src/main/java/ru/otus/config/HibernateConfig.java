package ru.otus.config;

import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private final Configuration configuration;

    public HibernateConfig() {
        this.configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
    }

    public String getUrl() {
        return configuration.getProperty("hibernate.connection.url");
    }

    public String getUser() {
        return configuration.getProperty("hibernate.connection.username");
    }

    public String getPass() {
        return configuration.getProperty("hibernate.connection.password");
    }

    public Configuration get() {
        return configuration;
    }
}
