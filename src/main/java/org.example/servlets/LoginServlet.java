package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;
import org.example.DTO.*;
import org.example.models.Person;
import org.example.services.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        super.init();
        loginService = new LoginService(new PersonDB());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PersonDTO personDTO = new Gson().fromJson(request.getReader(), PersonDTO.class);
        Optional<Person> personOptional = loginService.getPerson(personDTO);
        if(personOptional.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("person", personOptional.get());
            Person person = PersonDB.select(personOptional.get().getId());
            String json = new Gson().toJson(person);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(200);
        }
        else {
            response.setStatus(401);//?
        }
    }
}
