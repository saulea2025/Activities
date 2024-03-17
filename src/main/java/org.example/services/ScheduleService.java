package org.example.services;

import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;
import org.example.models.ScheduleForPDF;

import java.util.List;

public class ScheduleService {
    private ScheduleDB scheduleDB;

    public ScheduleService(ScheduleDB scheduleDB) {

        this.scheduleDB = scheduleDB;
    }

    public int add(int activityId, int personId) {

        return scheduleDB.add(activityId, personId);
    }

    public List<ScheduleForPDF> getSchedule() {

        return scheduleDB.getSchedule();
    }
}
