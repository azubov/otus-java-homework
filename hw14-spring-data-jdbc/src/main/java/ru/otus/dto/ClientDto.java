package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private Set<String> phones = new HashSet<>();

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().toString();
        this.phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.toSet());
    }
}
