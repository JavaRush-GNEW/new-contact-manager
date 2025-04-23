package ua.com.javarush.gnew.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.repository.ContactRepository;

@Slf4j
@WebServlet("/api/v1/contact")
public class ContactControllerAPI extends HttpServlet {

  private final ContactRepository contactRepository = new ContactRepository();
  private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    String idParam = req.getParameter("id");
    log.info("Received GET request with id: {}", idParam);

    if (idParam == null || idParam.isEmpty()) {
      log.warn("Missing or empty id parameter");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().println(gson.toJson(Map.of("error", "Missing id parameter")));
      return;
    }

    try {
      int id = Integer.parseInt(idParam);
      Optional<Contact> contactOpt = contactRepository.find(id);
      if (contactOpt.isPresent()) {
        log.info("Contact found with id: {}", id);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(gson.toJson(contactOpt.get()));
      } else {
        log.warn("Contact not found with id: {}", id);
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().println(gson.toJson(Map.of("error", "Contact not found")));
      }
    } catch (NumberFormatException e) {
      log.error("Invalid id format: {}", idParam, e);
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().println(gson.toJson(Map.of("error", "Invalid id format")));
    } catch (Exception e) {
      log.error("Error processing GET request", e);
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().println(gson.toJson(Map.of("error", "Internal server error")));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    try (BufferedReader reader = req.getReader()) {
      Contact newContact = gson.fromJson(reader, Contact.class);
      if (newContact == null) {
        log.warn("Received null contact in POST");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println(gson.toJson(Map.of("error", "Invalid request body")));
        return;
      }

      // Set the parent relationship for each associated entity
      newContact.getEmails().forEach(email -> email.setContact(newContact));
      newContact.getPhones().forEach(phone -> phone.setContact(newContact));
      newContact.getNetworks().forEach(network -> network.setContact(newContact));

      contactRepository.save(newContact);
      log.info("New contact created: {}", newContact);
      resp.setStatus(HttpServletResponse.SC_CREATED);
      resp.getWriter().println(gson.toJson(newContact));
    } catch (Exception e) {
      log.error("Error creating contact", e);
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().println(gson.toJson(Map.of("error", "Error creating contact")));
    }
  }
}
