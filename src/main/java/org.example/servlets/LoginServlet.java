package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;
import org.example.DTO.ActivityDTO;
import org.example.DTO.IdDTO;
import org.example.DTO.PersonDTO;
import org.example.models.Person;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PersonDTO personDTO = new Gson().fromJson(request.getReader(), PersonDTO.class);
        Optional<Person> personOptional = PersonDB.findByEmailAndPassword(personDTO.getEmail(), personDTO.getPassword());
        if(personOptional.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("person", personOptional.get());
            response.setStatus(200);
        }
        else {
            response.setStatus(401);//?
        }
    }
}
