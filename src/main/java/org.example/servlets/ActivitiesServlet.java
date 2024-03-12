package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DAO.PersonActivityDB;
import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "activities", value = "/activities")
public class ActivitiesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<PersonActivityDTO> activities = PersonActivityDB.select();
        String json = new Gson().toJson(activities);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ActivityDTO activityDTO = new Gson().fromJson(request.getReader(), ActivityDTO.class);
        ActivityDB.add(activityDTO);
        response.setStatus(200);
    }
}
