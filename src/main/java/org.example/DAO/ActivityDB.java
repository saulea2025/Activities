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
