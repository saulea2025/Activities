package org.example.DAO;

import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.models.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDB {
    private static String url = "jdbc:postgresql://192.168.100.31:5432/activities";
    private static String username = "postgres";
    private static String password = "1234";
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
}
