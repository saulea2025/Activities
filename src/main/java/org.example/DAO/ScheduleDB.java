package org.example.DAO;

import org.example.models.ScheduleForPDF;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDB {
    public int add(int activityId, int personId) {
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection()){
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
    public static List<ScheduleForPDF> getSchedule() {
        List<ScheduleForPDF> schedules = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement("SELECT schedule.date AS \"Time of Start\", " +
                         "person.name AS \"Person's Name\", " +
                         "activity.name AS \"Task\", " +
                         "activity.priority AS \"Priority\", " +
                         "activity.status AS \"Status\" " +
                         "FROM schedule " +
                         "LEFT JOIN activity ON schedule.activity_id = activity.id " +
                         "LEFT JOIN person ON schedule.person_id = person.id");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                while (resultSet.next()) {
                    Timestamp time = resultSet.getTimestamp("Time of Start");
                    String activityName = resultSet.getString("Person's Name");
                    String task = resultSet.getString("Task");
                    String priority = resultSet.getString("Priority");
                    String status = resultSet.getString("Status");

                    // Assuming ScheduleForPDF constructor accepts Timestamp for time
                    ScheduleForPDF schedule = new ScheduleForPDF(time, activityName, task, priority, status);
                    schedules.add(schedule);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            // Properly handle the exception
            ex.printStackTrace(); // Logging the exception
        }
        return schedules;
    }
}
