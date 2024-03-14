package org.example.senders;

import org.example.DAO.DatabaseConnector;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelegramSender extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Do nothing for now
    }

    @Override
    public String getBotUsername() {
        return "org.example.senders.TelegramSender";
    }

    @Override
    public String getBotToken() {
        return "7073523400:AAFnwhrbWRB1axfjNYQM4vM-83T8IRkp9Yw"; // Replace with your Telegram bot token
    }

    public void run() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            connection = DatabaseConnector.getConnection(); // Use DatabaseConnector to get connection
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return;
            }

            // Prepare SQL query to retrieve Telegram IDs from the person table
            String query = "SELECT telegram FROM person";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Iterate through the result set and send PDF to each Telegram user
            while (resultSet.next()) {
                String chatId = resultSet.getString("telegram");

                // Create a PDF
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DataToPdf.generatePdfFromResultSet(outputStream);

                // Send the PDF to Telegram
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(new InputFile(new ByteArrayInputStream(outputStream.toByteArray()), "report.pdf"));

                execute(sendDocument); // Send to the chat ID retrieved from the database
            }
        } catch (SQLException | TelegramApiException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
