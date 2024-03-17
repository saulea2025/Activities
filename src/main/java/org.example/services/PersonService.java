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

/*    public Person findPersonById(int id) {
        return PersonDB.getById(id);
    }

    public String getTelegramById(int id) {
        return PersonDB.getTelegram(id);
    }*/

    public Person selectPerson(int id) {
        return personDB.select(id);
    }
}
