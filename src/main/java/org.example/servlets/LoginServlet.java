package org.example.servlets;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.DAO.PersonDB;
import org.example.DTO.*;
import org.example.models.Person;
import org.example.senders.ScheduledMain;
import org.example.services.PersonService;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Optional;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    private PersonService personService;

    @Override
    public void init() throws ServletException {
        super.init();
        personService = new PersonService(new PersonDB());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduledMain.scheduleExecution(22, 53);


        PersonDTO personDTO = new Gson().fromJson(request.getReader(), PersonDTO.class);
        Optional<Person> personOptional = personService.getPerson(personDTO);
        if(personOptional.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("person", personOptional.get());
            Person person = PersonDB.select(personOptional.get().getId());
            //addJWT(person, response);
            String json = new Gson().toJson(person);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(200);
        }
        else {
            response.setStatus(401);
        }
    }
/*    private void addJWT(Person person, HttpServletResponse response) throws IOException {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = Jwts.builder()
                .setSubject(person.getEmail()) // Используем email как subject токена
                .signWith(key) // Подписываем токен секретным ключом
                .compact();
        System.out.println(token);
        response.setContentType("application/json");
        response.getWriter().write(token);
    }*/
}
