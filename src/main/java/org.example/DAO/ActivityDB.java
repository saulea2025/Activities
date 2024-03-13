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
import java.util.List;
import java.util.Properties;

public class ActivityDB {
    private static String url;
    private static String username;
    private static String password;
    private static Properties propertiesDB;
    static{
        propertiesDB = new Properties();
        try {
            propertiesDB.load(ActivityDB.class.getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = propertiesDB.getProperty("url");
        username = propertiesDB.getProperty("username");
        password = propertiesDB.getProperty("password");

    }
    public static List<PersonActivityDTO> select() {

        List<PersonActivityDTO> activities = new ArrayList<PersonActivityDTO>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
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
                        System.out.println("name: " + name);
                        String surname = resultSet.getString(6);
                        System.out.println("surname: " + surname);
                        PersonActivityDTO activity = new PersonActivityDTO(id, activityName, priority, status, name, surname);
                        System.out.println(activity);
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
    public static int add(ActivityDTO activityDTO) {
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
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
    public static List<ActivityDTO> selectByPerson(int personId) {

        List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select id, name, priority, status from activity\n" +
                        "WHERE person_id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(personId, 1);
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
    public static void setPersonForActivity(int activityId, int personId) {

        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "Update activity set person_id=? where id=? ";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, personId);
                    preparedStatement.setInt(2, activityId);
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
}
