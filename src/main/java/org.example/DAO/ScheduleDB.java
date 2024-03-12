package org.example.DAO;

import org.example.DTO.ActivityDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ScheduleDB {
    private static String url = "jdbc:postgresql://192.168.100.31:5432/activities";
    private static String username = "postgres";
    private static String password = "1234";
    public static int add(int activityId, int personId, Date date) {
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "INSERT INTO person_activity_schedule (activity_id, person_id, date) Values (?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, activityId);
                    preparedStatement.setInt(2, personId);
                    preparedStatement.setDate(3, date);
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
}
