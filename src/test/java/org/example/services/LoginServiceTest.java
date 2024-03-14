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

class LoginServiceTest {
    private PersonDB personDBmock;
    LoginService loginService;
    @BeforeEach
    public void setUp(){
        personDBmock = mock(PersonDB.class);
        loginService = new LoginService(personDBmock);
        Optional<Person> personOptional = Optional.of(new Person(1, "alena", "ch", "admin", "email", "tg", "correctPass"));
        when(personDBmock.findByEmailAndPassword("email", "correctPass")).thenReturn(personOptional);

    }
    @Test
    void getPersonNotNull() {
        PersonDTO personDTO = new PersonDTO("email", "correctPass");
        assertTrue(loginService.getPerson(personDTO).isPresent());
    }
    @Test
    void getPersonNull() {
        PersonDTO personDTO = new PersonDTO("email", "wrongPass");
        assertFalse(loginService.getPerson(personDTO).isPresent());
    }
/*    public Optional<Person> getPerson(PersonDTO personDTO){
        PersonDB personDB = new PersonDB();
        return personDB.findByEmailAndPassword(personDTO.getEmail(), personDTO.getPassword());
    }*/

}