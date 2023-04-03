package ru.otus.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.AuthService;
import ru.otus.services.TemplateProcessor;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

import java.util.Arrays;

public class WebServerWithFilterBasedSecurity extends WebServerSimple {

    private final AuthService authService;

    public WebServerWithFilterBasedSecurity(int port, DBServiceClient clientService, TemplateProcessor templateProcessor, AuthService authService) {
        super(port, clientService, templateProcessor);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        var authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path ->
                servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
