package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Client;

import java.util.List;

@Repository
public interface ClientDao extends CrudRepository<Client, Long> {

    List<Client> findAll();
}
