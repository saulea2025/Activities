package org.example.servlets;

import org.example.senders.ScheduledMain;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PdfSenderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ScheduledMain.scheduleExecution(22, 17);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
