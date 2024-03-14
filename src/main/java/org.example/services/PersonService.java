package org.example.services;

import org.example.DAO.PersonDB;
import org.example.DTO.PersonDTO;
import org.example.models.Person;

import java.util.Optional;

public class PersonService {
    private PersonDB personDB;

    public PersonService(PersonDB personDB) {
        this.personDB = personDB;
    }

    public Optional<Person> getPerson(PersonDTO personDTO){
        //PersonDB personDB = new PersonDB();
        return personDB.findByEmailAndPassword(personDTO.getEmail(), personDTO.getPassword());
    }
}
