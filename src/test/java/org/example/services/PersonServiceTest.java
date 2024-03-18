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

    @Test
    void selectPerson_ReturnsPersonWithCorrectId() {

        int personId = 1;
        Person expectedPerson = new Person(1, "alena", "ch", "admin", "email", "tg", "correctPass");
        when(personDBmock.select(personId)).thenReturn(expectedPerson);

        Person selectedPerson = personService.selectPerson(personId);

        assertNotNull(selectedPerson); // Убедимся, что возвращенный объект Person не null
        assertEquals(personId, selectedPerson.getId()); // Убедимся, что id возвращенного Person совпадает с ожидаемым id
    }

    @Test
    void select_ReturnsNullForInvalidId() {

        int invalidId = 999;
        when(personDBmock.select(invalidId)).thenReturn(null);

        Person selectedPerson = personService.selectPerson(invalidId);

        assertNull(selectedPerson); // Убедимся, что возвращенный объект Person равен null
    }
}