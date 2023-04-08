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
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = dao.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        var clientList = dao.findAll();
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
