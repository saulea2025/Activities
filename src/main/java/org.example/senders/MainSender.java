package org.example.senders;

public class MainSender {
    public static void main() {
        EmailSender emailSender = new EmailSender();
        emailSender.run();

        TelegramSender telegramSender = new TelegramSender();
        telegramSender.run();
    }
}

