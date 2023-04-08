package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.dao.ClientDao;
import ru.otus.model.Client;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientDao dao;
    private final TransactionManager transactionManager;

    @Override
    public List<Client> findAll() {
        var clientList = dao.findAll();
        log.info("all clients: {}", clientList);
        return clientList;
    }

    @Override
    public Client findById(Long id) {
        var client = dao.findById(id).orElseThrow();
        log.info("found client: {}", client);
        return client;
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = dao.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
        log.info("deleted client.id: {}", id);
    }
}
