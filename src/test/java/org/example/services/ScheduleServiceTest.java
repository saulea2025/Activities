package org.example.services;

import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;
import org.example.models.Person;
import org.example.models.ScheduleForPDF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    private ScheduleDB scheduleDBmock;
    ScheduleService scheduleService;
    @BeforeEach
    public void setUp(){
        scheduleDBmock = mock(ScheduleDB.class);
        scheduleService = new ScheduleService(scheduleDBmock);
    }

    @Test
    void addSchedule_SuccessfullyAddsScheduleEntry() {

        int activityId = 1;
        int personId = 1;
        when(scheduleDBmock.add(activityId, personId)).thenReturn(1);

        int result = scheduleService.add(activityId, personId);

        assertEquals(1, result);
        verify(scheduleDBmock, times(1)).add(activityId, personId); // Verify that the add method is called exactly once with the correct parameters
    }

    @Test
    void add_ReturnsZeroForInvalidId() {

        int invalidActivityId = -1;
        int invalidPersonId = -1;
        when(scheduleDBmock.add(invalidActivityId, invalidPersonId)).thenReturn(0);

        int result = scheduleService.add(invalidActivityId, invalidPersonId);

        assertEquals(0, result);
        verify(scheduleDBmock, times(1)).add(invalidActivityId, invalidPersonId); // Verify that the add method is called exactly once with the correct parameters
    }

/*    @Test
    void getSchedule_ReturnsNonEmptyList() {

        List<ScheduleForPDF> expectedSchedule = new ArrayList<>();
        expectedSchedule.add(new ScheduleForPDF(Timestamp.valueOf("2024-03-17 10:00:00"), "Meeting", "High", "In Progress"));
        expectedSchedule.add(new ScheduleForPDF(Timestamp.valueOf("2024-03-18 14:00:00"), "Presentation", "Medium", "New"));
        when(scheduleDBmock.getSchedule()).thenReturn(expectedSchedule);

        List<ScheduleForPDF> actualSchedule = scheduleService.getSchedule();

        assertEquals(expectedSchedule.size(), actualSchedule.size());
        for (int i = 0; i < expectedSchedule.size(); i++) {
            assertEquals(expectedSchedule.get(i), actualSchedule.get(i));
            assertNotNull(actualSchedule); // Проверяем, что возвращенный список не равен null
            assertFalse(actualSchedule.isEmpty()); // Проверяем, что возвращенный список не пустой
        }
    }*/

    @Test
    void getSchedule_ReturnsEmptyListWhenNoSchedule() {

        List<ScheduleForPDF> expectedEmptySchedule = new ArrayList<>();
        when(scheduleDBmock.getSchedule()).thenReturn(expectedEmptySchedule);

        List<ScheduleForPDF> actualSchedule = scheduleService.getSchedule();

        assertNotNull(actualSchedule); // Проверяем, что возвращенный список не равен null
        assertTrue(actualSchedule.isEmpty()); // Проверяем, что возвращенный список пустой
    }

    @Test
    void getSchedule_HandlesDatabaseException() {

        when(scheduleDBmock.getSchedule()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> scheduleService.getSchedule());
    }
}