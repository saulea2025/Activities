package org.example.servlets;

import org.example.senders.ScheduledMain;
import org.example.senders.TelegramSender;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PdfSenderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        TelegramSender telegramSender = new TelegramSender();
        //telegramSender.run();
        ScheduledMain.scheduleExecution(22, 51);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
