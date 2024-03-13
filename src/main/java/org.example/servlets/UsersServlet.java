package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;
import org.example.DTO.ActivityDTO;
import org.example.DTO.IdDTO;
import org.example.models.Person;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "users", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int personId = getId(request);
        List<ActivityDTO> activities = ActivityDB.selectByPerson(personId);
        String json = new Gson().toJson(activities);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int personId = getId(request);
        int activityId = new Gson().fromJson(request.getReader(), IdDTO.class).getId();
        ScheduleDB.add(activityId, personId);
        response.setStatus(200);
    }
    private int getId(HttpServletRequest request){
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");
        return person.getId();
    }
}
