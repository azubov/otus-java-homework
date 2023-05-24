package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        if (model.getAttribute("client") == null) {
            model.addAttribute("client", new ClientDto());
        }
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @PostMapping("/clients")
    public RedirectView saveClient(ClientDto dto) {
        clientService.save(new Client(dto));
        return new RedirectView("/clients", true);
    }

    @PutMapping("/clients/{id}")
    public String updateClient(@PathVariable("id") Long id, Model model) {
        var clientForUpdate = clientService.findById(id);
        model.addAttribute("client", new ClientDto(clientForUpdate));
        return clientsListView(model);
    }

    @DeleteMapping("/clients/{id}")
    public RedirectView deleteClient(@PathVariable("id") Long id) {
        clientService.delete(id);
        return new RedirectView("/clients", true);
    }
}
