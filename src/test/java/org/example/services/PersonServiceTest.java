package org.example.services;

import org.example.DAO.PersonDB;
import org.example.DTO.PersonDTO;
import org.example.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonServiceTest {
    private PersonDB personDBmock;
    PersonService personService;
    @BeforeEach
    public void setUp(){
        personDBmock = mock(PersonDB.class);
        personService = new PersonService(personDBmock);
        Optional<Person> personOptional = Optional.of(new Person(1, "alena", "ch", "admin", "email", "tg", "correctPass"));
        when(personDBmock.findByEmailAndPassword("email", "correctPass")).thenReturn(personOptional);

    }
    @Test
    void getPersonNotNull() {
        PersonDTO personDTO = new PersonDTO("email", "correctPass");
        assertTrue(personService.getPerson(personDTO).isPresent());
    }
    @Test
    void getPersonNull() {
        PersonDTO personDTO = new PersonDTO("email", "wrongPass");
        assertFalse(personService.getPerson(personDTO).isPresent());
    }

}