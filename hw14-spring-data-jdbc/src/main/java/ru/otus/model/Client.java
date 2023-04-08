package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.dto.ClientDto;

import java.util.Set;

@Getter
@Table("client")
public class Client extends BaseId {

    private final String name;
    @MappedCollection(idColumn = "client_id")
    private final Address address;
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(ClientDto dto) {
        this(null, dto.getName(), new Address(dto.getAddress()), Set.of(new Phone(dto.getPhone())));
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        super(id);
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
