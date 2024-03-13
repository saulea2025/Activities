package org.example.senders;

public class MainSender {

    public static void start() {
        // Generate PDF report and send via email

        EmailSender emailSender = new EmailSender();
        emailSender.init();

        // Start Telegram bot to send PDF report
        TelegramSender bot = new TelegramSender();
        bot.run();
    }
}
