package org.example.services;

public class Main {
    public static void main(String[] args) {
        // Create an instance of EmailSender
        EmailSender emailSender = new EmailSender();

        // Load properties
        emailSender.setProps();

        // Call sendPdfReport method with appropriate parameters
        String header = "Monthly Report";
        String text = "This is the monthly report for March 2024.";
        String recipientEmail = "saule.anafinova@gmail.com"; // Replace with recipient's email address
        emailSender.sendPdfReport(header, text, recipientEmail);
    }
}
