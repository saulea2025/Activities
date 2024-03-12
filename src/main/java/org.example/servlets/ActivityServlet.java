package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.PersonActivityDB;
import org.example.DTO.IdDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "activity", urlPatterns = {"/activities/*"})
public class ActivityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int personId = new Gson().fromJson(request.getReader(), IdDTO.class).getId();
        int activityId =Integer.parseInt(request.getPathInfo().substring(1));
        PersonActivityDB.setPersonForActivity(activityId, personId);
        response.setStatus(200);
    }
}
