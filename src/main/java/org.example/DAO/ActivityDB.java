package org.example.DAO;

import lombok.SneakyThrows;
import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.models.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class ActivityDB {
    public List<PersonActivityDTO> select() {

        List<PersonActivityDTO> activities = new ArrayList<PersonActivityDTO>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection()){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select activity.id, activity.name, activity.priority, activity.status, person.name, person.surname\n" +
                        "from activity left join person\n" +
                        "on activity.person_id = person.id";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String activityName = resultSet.getString(2);
                        String priority = resultSet.getString(3);
                        String status = resultSet.getString(4);
                        String name = resultSet.getString(5);
                        String surname = resultSet.getString(6);
                        PersonActivityDTO activity = new PersonActivityDTO(id, activityName, priority, status, name, surname);
                        activities.add(activity);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return activities;
    }
    public int add(ActivityDTO activityDTO) {
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection()){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "INSERT INTO activity (name, priority, status) Values (?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, activityDTO.getName());
                    preparedStatement.setString(2, activityDTO.getPriority());
                    preparedStatement.setString(3, activityDTO.getStatus());
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    public List<ActivityDTO> selectByPerson(int personId) {

        List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection()){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select id, name, priority, status from activity\n" +
                        "WHERE person_id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, personId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String activityName = resultSet.getString(2);
                        String priority = resultSet.getString(3);
                        String status = resultSet.getString(4);
                        ActivityDTO activity = new ActivityDTO(id, activityName, priority, status);
                        activities.add(activity);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return activities;
    }
    public int setPersonForActivity(int activityId, int personId) {
        int chanchedRows = 0;
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection()){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "Update activity set person_id=? where id=? ";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, personId);
                    preparedStatement.setInt(2, activityId);
                    chanchedRows = preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return chanchedRows;
    }
    public int changeStatus(int activityId, String status) {
        int chanchedRows = 0;
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection()){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "Update activity set status=? where id=? ";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, status);
                    preparedStatement.setInt(2, activityId);
                    chanchedRows = preparedStatement.executeUpdate();

                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return chanchedRows;
    }
}
