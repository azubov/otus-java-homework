package ru.otus.crm.service;

import ru.otus.cachehw.MyCache;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class CachedDbServiceClientImpl implements DBServiceClient {

    private final DBServiceClient service;
    private final MyCache<Long, Client> cache;

    public CachedDbServiceClientImpl(DBServiceClient service, MyCache<Long, Client> cache) {
        this.service = service;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        var savedClient = service.saveClient(client);
        cache.put(savedClient.getId(), savedClient);
        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        var optClient = Optional.ofNullable(cache.get(id));
        if (optClient.isPresent()) {
            return optClient;
        }
        return findInDbAndUpdateCache(id);
    }

    @Override
    public List<Client> findAll() {
        return service.findAll();
    }

    private Optional<Client> findInDbAndUpdateCache(long id) {
        var optClient = service.getClient(id);
        if (optClient.isPresent()) {
            var client = optClient.get();
            cache.put(client.getId(), client);
        }
        return optClient;
    }
}
