package org.example.services;

import org.example.DAO.ActivityDB;
import org.example.DAO.PersonDB;
import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.DTO.PersonDTO;
import org.example.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void addActivity_SuccessfullyAddsNewActivity() {

        ActivityDTO activityDTO = new ActivityDTO(1,"Task 3", "Low", "New");
        when(activityDBmock.add(activityDTO)).thenReturn(1);

        int numberOfAffectedRows = activitiesService.addActivity(activityDTO);

        assertEquals(1, numberOfAffectedRows);
    }

    @Test
    void addActivity_UnsuccessfullyAddsNewActivity() {
        ActivityDTO activityDTO = new ActivityDTO(1,"Task 3", "Low", "New");
        when(activityDBmock.add(activityDTO)).thenReturn(0);

        int numberOfAffectedRows = activitiesService.addActivity(activityDTO);

        assertEquals(0, numberOfAffectedRows);
    }

    @Test
    void getActivities_ReturnsListOfPersonActivityDTO() {
        ArrayList<PersonActivityDTO> expectedActivities = new ArrayList<>();
        expectedActivities.add(new PersonActivityDTO(1, "Task 1", "High", "In Progress", "Ivan", "Ivanov"));
        expectedActivities.add(new PersonActivityDTO(2, "Task 2", "Medium", "Done", "Petr", "Petrov"));
        when(activityDBmock.select()).thenReturn(expectedActivities);

        List<PersonActivityDTO> actualActivities = activitiesService.getActivities();

        assertEquals(expectedActivities.size(), actualActivities.size());
        for (int i = 0; i < expectedActivities.size(); i++) {
            assertEquals(expectedActivities.get(i), actualActivities.get(i));
        }
    }

    @Test
    void selectByPerson_ReturnsNonEmptyListOfActivityDTO() {

        int personId = 1;
        ArrayList<ActivityDTO> expectedActivities = new ArrayList<>();
        expectedActivities.add(new ActivityDTO(1, "Task 1", "High", "In Progress"));
        expectedActivities.add(new ActivityDTO(2, "Task 2", "Medium", "New"));
        when(activityDBmock.selectByPerson(personId)).thenReturn(expectedActivities);

        List<ActivityDTO> actualActivities = activitiesService.selectByPerson(personId);

        assertEquals(expectedActivities.size(), actualActivities.size());
        for (int i = 0; i < expectedActivities.size(); i++) {
            assertEquals(expectedActivities.get(i), actualActivities.get(i));
        assertNotNull(actualActivities); // Проверяем, что возвращаемый список не равен null
        assertFalse(actualActivities.isEmpty()); // Проверяем, что возвращаемый список не пустой
        }
    }
    @Test
    void selectByPerson_ReturnsEmptyListWhenNoActivities() {

        int personId = 1;
        when(activityDBmock.selectByPerson(personId)).thenReturn(Collections.emptyList());

        List<ActivityDTO> actualActivities = activitiesService.selectByPerson(personId);

        assertNotNull(actualActivities); // Проверяем, что возвращаемый список не равен null
        assertTrue(actualActivities.isEmpty()); // Проверяем, что возвращаемый список пустой
    }
}