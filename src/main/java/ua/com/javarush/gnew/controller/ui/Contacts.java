package ua.com.javarush.gnew.controller.ui;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.repository.ContactRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/contacts")
public class Contacts extends HttpServlet {

    private final ContactRepository contactRepository = new ContactRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Contact> all = contactRepository.getAll();

        req.setAttribute("contacts", all);

        req.getRequestDispatcher("/WEB-INF/views/contacts.jsp").forward(req, resp);
    }
}
