package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR = "clients";

    private final DBServiceClient clientService;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(DBServiceClient clientService, TemplateProcessor templateProcessor) {
        this.clientService = clientService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var clients = clientService.findAll();

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR, clients);

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        clientService.saveClient(extractClientFromRequest(req));
        resp.sendRedirect(req.getContextPath() + "/clients");
    }

    private Client extractClientFromRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key,value) -> params.put(key, value[0]));

        return new Client(
                params.get("name"),
                new Address(params.get("address")),
                List.of(new Phone(params.get("phone"))));
    }
}
