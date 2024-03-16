package org.example.servlets;

import org.example.senders.MainSender;
import org.example.senders.ScheduledMain;

import javax.mail.MailSessionDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name="InitializeResources", urlPatterns="/initializeResources", loadOnStartup=1)
public class PdfSenderServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        ScheduledMain.scheduleExecution(22, 7);
    }
}
