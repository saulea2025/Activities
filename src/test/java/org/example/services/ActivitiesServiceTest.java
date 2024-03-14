package org.example.services;

import org.example.DAO.ActivityDB;
import org.example.DAO.PersonDB;
import org.example.DTO.PersonDTO;
import org.example.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivitiesServiceTest {
    private ActivityDB activityDBmock;
    ActivitiesService activitiesService;
    @BeforeEach
    public void setUp(){
        activityDBmock = mock(ActivityDB.class);
        activitiesService = new ActivitiesService(activityDBmock);
        Optional<Person> personOptional = Optional.of(new Person(1, "alena", "ch", "admin", "email", "tg", "correctPass"));
        when(activityDBmock.changeStatus(1, "done")).thenReturn(1);
        when(activityDBmock.setPersonForActivity(3, 3)).thenReturn(1);
        ////////////


    }
    @Test
    void changeStatusSuccess() {

        assertEquals(1, activitiesService.changeStatus(1, "done"));
    }
    @Test
    void changeStatusNotSuccess() {

        assertEquals(0, activitiesService.changeStatus(1000000, "done"));
    }
    @Test
    void setPersonForActivitySuccess(){
        assertEquals(1, activitiesService.setPersonForActivity(3, 3));
    }
    @Test
    void setPersonForActivityNotSuccess(){
        assertEquals(0, activitiesService.setPersonForActivity(1000, 1000));
    }

}