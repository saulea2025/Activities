package org.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PersonDB {
    private static String url = "jdbc:postgresql://192.168.100.31:5432/activities";
    private static String username = "postgres";
    private static String password = "1234";
    public static void setCurrentActivity(int activityId, int personId) {
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "ALTER TABLE person SET current_activity_id=? WHERE id=?";
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
