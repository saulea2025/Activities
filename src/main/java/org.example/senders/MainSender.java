package org.example.senders;

public class MainSender {
    public static void main() {
        // Initialize EmailSender
        EmailSender emailSender = new EmailSender();
        // Call method to send emails
        emailSender.run();

        // Initialize TelegramSender
        TelegramSender telegramSender = new TelegramSender();
        // Call method to send Telegram messages
        telegramSender.run();
    }
}

