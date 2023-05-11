package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    void delete(Long id);
}
