package ua.com.javarush.gnew.controller.api;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.repository.ContactRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/v1/contact")
public class ContactControllerAPI extends HttpServlet {

//    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactRepository contactRepository = new ContactRepository();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(gson.toJson(Map.of("error", "Missing id parameter")));
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Optional<Contact> contactOpt = contactRepository.get(id);
            if (contactOpt.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(gson.toJson(contactOpt.get()));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println(gson.toJson(Map.of("error", "Contact not found")));
            }
        } catch (NumberFormatException e) {
//            logger.error("Invalid id format: {}", idParam, e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(gson.toJson(Map.of("error", "Invalid id format")));
        } catch (Exception e) {
//            logger.error("Error processing GET request", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(gson.toJson(Map.of("error", "Internal server error")));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try (BufferedReader reader = req.getReader()) {
            Contact newContact = gson.fromJson(reader, Contact.class);
            if (newContact == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(gson.toJson(Map.of("error", "Invalid request body")));
                return;
            }

            // Set the parent relationship for each associated entity
            newContact.getEmails().forEach(email -> email.setContact(newContact));
            newContact.getPhones().forEach(phone -> phone.setContact(newContact));
            newContact.getNetworks().forEach(network -> network.setContact(newContact));

            contactRepository.save(newContact);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println(gson.toJson(newContact));
        } catch (Exception e) {
//            logger.error("Error creating contact", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(gson.toJson(Map.of("error", "Error creating contact")));
        }
    }
}
