package org.example.DAO;

import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.models.ScheduleForPDF;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ScheduleDB {
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
    public int add(int activityId, int personId) {
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "INSERT INTO schedule (activity_id, person_id, date) Values (?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, activityId);
                    preparedStatement.setInt(2, personId);
                    preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    /*public static List<ScheduleForPDF> getSchedulesForPerson(int personId) {
        List<ScheduleForPDF> schedules = new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "SELECT date, activity.name, activity.priority, activity.status from schedule " +
                        "left join activity on schedule.activity_id=activity.id where schedule.person_id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, personId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Timestamp time = resultSet.getTimestamp(1);
                        String activityName = resultSet.getString(2);
                        String priority = resultSet.getString(3);
                        String status = resultSet.getString(4);

                        ScheduleForPDF schedule = new ScheduleForPDF(time, activityName, priority, status);
                        schedules.add(schedule);
                    }

                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return schedules;
    }*/
    public List<ScheduleForPDF> getSchedule() {
        List<ScheduleForPDF> schedules = new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "SELECT date, activity.name, activity.priority, activity.status from schedule " +
                        "left join activity on schedule.activity_id=activity.id";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Timestamp time = resultSet.getTimestamp(1);
                        String activityName = resultSet.getString(2);
                        String priority = resultSet.getString(3);
                        String status = resultSet.getString(4);
                        ScheduleForPDF schedule = new ScheduleForPDF(time, activityName, priority, status);
                        schedules.add(schedule);
                    }

                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return schedules;
    }
}
