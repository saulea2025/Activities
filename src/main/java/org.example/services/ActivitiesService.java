package org.example.services;

import org.example.DAO.ActivityDB;
import org.example.DAO.PersonDB;
import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.DTO.PersonDTO;
import org.example.models.Person;

import java.util.List;
import java.util.Optional;

public class ActivitiesService {
    private ActivityDB activityDB;

    public ActivitiesService(ActivityDB activityDB) {
        this.activityDB = activityDB;
    }

    public List<PersonActivityDTO> getActivities(){
        return activityDB.select();
    }
    public void addActivity(ActivityDTO activityDTO){
        activityDB.add(activityDTO);
    }
    public int changeStatus(int activityId, String status){
        return activityDB.changeStatus(activityId, status);
    }
}
