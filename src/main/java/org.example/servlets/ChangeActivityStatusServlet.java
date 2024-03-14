package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DTO.ActivityStatusDTO;
import org.example.DTO.IdDTO;
import org.example.services.ActivitiesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "changeActivityStatus", urlPatterns = {"/users/*"})
public class ChangeActivityStatusServlet extends HttpServlet {
    private ActivitiesService activitiesService;

    @Override
    public void init() throws ServletException {
        super.init();
        activitiesService = new ActivitiesService(new ActivityDB());
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String status = new Gson().fromJson(request.getReader(), ActivityStatusDTO.class).getStatus();
        int activityId =Integer.parseInt(request.getPathInfo().substring(1));
        if(activitiesService.changeStatus(activityId, status)>0)
            response.setStatus(200);
        else
            response.setStatus(400);
    }
}
