package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DTO.IdDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "setPersonForActivity", urlPatterns = {"/activities/*"})
public class SetPersonForActivityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int personId = new Gson().fromJson(request.getReader(), IdDTO.class).getId();
        int activityId =Integer.parseInt(request.getPathInfo().substring(1));
        ActivityDB.setPersonForActivity(activityId, personId);
        response.setStatus(200);
    }
}
