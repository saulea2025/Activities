package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int id;
    private String name;
    private String surname;
    private String role;
    private String email;
    private String telegram;

    public Person(String name, String surname, String role, String email, String telegram) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.email = email;
        this.telegram = telegram;
    }

}
