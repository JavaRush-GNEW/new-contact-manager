package ua.com.javarush.gnew.controller.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.repository.ContactRepository;

@WebServlet("/contacts")
public class ContactsServlet extends BaseServlet {

  private final ContactRepository contactRepository = new ContactRepository();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    List<Contact> all = contactRepository.getAll();
  }
}
