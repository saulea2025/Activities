package org.example.services;

import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;

public class ScheduleService {
    private ScheduleDB scheduleDB;

    public ScheduleService(ScheduleDB scheduleDB) {
        this.scheduleDB = scheduleDB;
    }

    public void add(int activityId, int personId) {
        scheduleDB.add(activityId, personId);
    }
}
