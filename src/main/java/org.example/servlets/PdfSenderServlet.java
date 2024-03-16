package org.example.servlets;

import org.example.senders.MainSender;
import org.example.senders.ScheduledMain;

import javax.mail.MailSessionDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "sender", value = "/...", loadOnStartup = 0)
public class PdfSenderServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        ScheduledMain.scheduleExecution(14, 50);
    }
}
