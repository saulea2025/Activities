package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DTO.ActivityStatusDTO;
import org.example.DTO.IdDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "changeActivityStatus", urlPatterns = {"/users/*"})
public class ChangeActivityStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String status = new Gson().fromJson(request.getReader(), ActivityStatusDTO.class).getStatus();
        int activityId =Integer.parseInt(request.getPathInfo().substring(1));
        ActivityDB.changeStatus(activityId, status);
        response.setStatus(200);
    }
}
