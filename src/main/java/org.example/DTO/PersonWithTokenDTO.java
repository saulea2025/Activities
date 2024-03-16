package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.models.Person;

@Getter
@Setter
@NoArgsConstructor
public class PersonWithTokenDTO {
    private int id;
    private String name;
    private String surname;
    private String role;
    private String email;
    private String telegram;
    private String token;

    public PersonWithTokenDTO(Person person, String token) {
        this.id = person.getId();
        this.name = person.getName();
        this.surname = person.getSurname();
        this.role = person.getRole();
        this.email = person.getEmail();
        this.telegram = person.getTelegram();
        this.token = token;
    }
}
