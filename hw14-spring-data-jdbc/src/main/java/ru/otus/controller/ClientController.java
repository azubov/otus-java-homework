package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDto;
import ru.otus.model.Client;
import ru.otus.service.ClientService;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        model.addAttribute("client", new ClientDto());
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @PostMapping("/clients")
    public RedirectView saveClient(ClientDto dto) {
        clientService.saveClient(new Client(dto));
        return new RedirectView("/clients", true);
    }
}
