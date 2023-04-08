package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.dto.ClientDto;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Table("client")
public class Client extends BaseId {

    private final String name;
    @MappedCollection(idColumn = "client_id")
    private final Address address;
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(ClientDto dto) {
        this(dto.getId(),
                dto.getName(),
                new Address(dto.getAddress()),
                dto.getPhones().stream().map(Phone::new).collect(Collectors.toSet()));
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
                ", name=" + name +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
