package org.example.DAO;

import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.models.Activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonActivityDB {
    private static String url = "jdbc:postgresql://192.168.100.31:5432/activities";
    private static String username = "postgres";
    private static String password = "1234";
    public static List<PersonActivityDTO> select() {

        List<PersonActivityDTO> activities = new ArrayList<PersonActivityDTO>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select activity.name, activity.priority, activity.status, person.name, person.surname\n" +
                        "from activity left join person_activity\n" +
                        "on activity.id = person_activity.activity_id\n" +
                        "left join person\n" +
                        "on person_activity.person_id = person.id";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        String activityName = resultSet.getString(1);
                        String priority = resultSet.getString(2);
                        String status = resultSet.getString(3);
                        String name = resultSet.getString(4);
                        String surname = resultSet.getString(5);
                        PersonActivityDTO activity = new PersonActivityDTO(activityName, priority, status, name, surname);
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
    public static List<ActivityDTO> selectByPerson(int personId) {

        List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select activity.name, activity.priority, activity.status\n" +
                        "from activity left join person_activity\n" +
                        "on activity.id = person_activity.activity_id\n" +
                        "WHERE person_activity.person_id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(personId, 1);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        String activityName = resultSet.getString(1);
                        String priority = resultSet.getString(2);
                        String status = resultSet.getString(3);
                        ActivityDTO activity = new ActivityDTO(activityName, priority, status);
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
                String sql = "INSERT INTO person_activity(activity_id, person_id) VALUES(?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, activityId);
                    preparedStatement.setInt(2, personId);
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

}
