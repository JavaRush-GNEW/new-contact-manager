package ua.com.javarush.gnew.controller.ui;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.repository.ContactRepository;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("")
public class HomeController extends BaseServlet {

    private final ContactRepository contactRepository = new ContactRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Contact> contacts = contactRepository.getAll();

        context.setVariable("projectName", "Contact Manager");
        context.setVariable("contacts", contacts);

        templateEngine.process("home", context, resp.getWriter());
    }
}
