package org.example.senders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataFetcher {
    private static final String QUERY = "SELECT s.id, p.name AS person, a.name AS activity, s.date " +
            "FROM schedule s " +
            "JOIN person p ON s.person_id = p.id " +
            "JOIN activity a ON s.activity_id = a.id";

    public static ResultSet fetchData() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnector.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QUERY);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            // Return null in case of exception
            return null;
        } finally {
            // Do not close resultSet, statement, and connection here
            // They need to be closed where the result set is consumed
        }
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        DatabaseConnector.close(resultSet);
        DatabaseConnector.close(statement);
        DatabaseConnector.close(connection);
    }
}