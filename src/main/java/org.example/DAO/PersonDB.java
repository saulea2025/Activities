package org.example.DAO;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.example.DTO.PersonActivityDTO;
import org.example.DTO.PersonDTO;
import org.example.models.Person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class PersonDB {
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
    public static Optional<Person> findByEmailAndPassword(String userEmail, String userPassword) {
        Optional<Person> personOptional = Optional.empty();
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select * FROM person where email=? and password=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, userEmail);
                    preparedStatement.setString(2, userPassword);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        String surname = resultSet.getString(3);
                        String role = resultSet.getString(4);
                        String email = resultSet.getString(5);
                        String telegram = resultSet.getString(6);
                        String password = resultSet.getString(7);
                        Person person = new Person(id, name, surname, role, email, telegram,  password);
                        personOptional = Optional.of(person);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return personOptional;
    }
    public static String getTelegram(int id) {
        String telegram = null;
        try{
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                String sql = "select telegram FROM person where id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        telegram = resultSet.getString(1);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return telegram;
    }
}
