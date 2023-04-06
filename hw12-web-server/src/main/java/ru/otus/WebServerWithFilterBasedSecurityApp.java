package ru.otus;

import ru.otus.config.HibernateConfig;
import ru.otus.config.MigrationsConfig;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.dao.InMemoryAdminDao;
import ru.otus.server.WebServerWithFilterBasedSecurity;
import ru.otus.services.AuthServiceImpl;
import ru.otus.services.TemplateProcessorImpl;

/*
    // Стартовая страница
    http://localhost:8080

    // Страница авторизации
    http://localhost:8080/login

    // Страница клиентов
    http://localhost:8080/clients
*/
public class WebServerWithFilterBasedSecurityApp {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {

        var config = new HibernateConfig();
        MigrationsConfig.create(config.getUrl(), config.getUser(), config.getPass());

        var sessionFactory = HibernateUtils
                .buildSessionFactory(config.get(), Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var clientService = new DbServiceClientImpl(transactionManager, clientTemplate);
        var templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        var adminDao = new InMemoryAdminDao();
        var authService = new AuthServiceImpl(adminDao);

        var webServer = new WebServerWithFilterBasedSecurity(WEB_SERVER_PORT, clientService, templateProcessor, authService);
        webServer.start();
        webServer.join();
    }
}
