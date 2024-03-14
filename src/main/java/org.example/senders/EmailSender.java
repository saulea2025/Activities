package org.example.senders;

import org.example.DAO.DatabaseConnector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class EmailSender {

    private Connection connection; // Assuming this is initialized elsewhere

    public EmailSender() {
        // Get the connection from DatabaseConnector
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Prepare SQL query to retrieve email addresses from the database
            String query = "SELECT email FROM person";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Iterate through the result set and send email to each recipient
            while (resultSet.next()) {
                String recipientEmail = resultSet.getString("email");
                sendEmailWithAttachment(recipientEmail, "PDF Report", "Please find attached the PDF report.", "report.pdf");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentFilename) {
        // Sender's email ID needs to be mentioned
        String from = "saule.anafinova@gmail.com"; // Update with your email

        // Assuming you are sending email from Gmail
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("saule.anafinova@gmail.com", "ypdu pvjj ifib hkfq"); // Update with your email and password
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataToPdf.generatePdfFromResultSet(outputStream); // You need to implement this method
            DataSource source = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentFilename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
