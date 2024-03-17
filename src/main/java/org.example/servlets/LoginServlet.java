package org.example.servlets;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.DAO.PersonDB;
import org.example.DTO.*;
import org.example.models.Person;
import org.example.senders.EmailSender;
import org.example.senders.ScheduledMain;
import org.example.senders.TelegramSender;
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
        TelegramSender telegramSender = new TelegramSender();
        telegramSender.run();
        EmailSender emailSender = new EmailSender();
        emailSender.run();
        PersonDTO personDTO = new Gson().fromJson(request.getReader(), PersonDTO.class);
        Optional<Person> personOptional = personService.getPerson(personDTO);
        if(personOptional.isPresent()) {

            HttpSession session = request.getSession();
            session.setAttribute("person", personOptional.get());
            System.out.println("set attribute");
            Person person = PersonDB.select(personOptional.get().getId());
            PersonWithTokenDTO personWithTokenDTO = new PersonWithTokenDTO(person, generateJWT(person.getEmail()));
            String json = new Gson().toJson(personWithTokenDTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.setStatus(200);
        }
        else {
            response.setStatus(401);
        }
    }
    private String generateJWT(String email) throws IOException {
        //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = Jwts.builder()
                .setSubject(email) // Используем email как subject токена
                .signWith(SignatureAlgorithm.HS256, "your-secret-key-your-secret-key-your-secret-key".getBytes(StandardCharsets.UTF_8)) // Подписываем токен секретным ключом
                .compact();
        return token;
    }

}
