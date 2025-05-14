package ua.com.javarush.gnew.repository;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.util.HibernateUtil;

@Slf4j
public class ContactRepository extends BaseRepository<Contact, Integer> {

  public ContactRepository() {
    super(Contact.class);
  }

  public List<Contact> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      log.debug("Retrieving all contacts");
      Query<Contact> query = session.createQuery("from Contact");
      List<Contact> contacts = query.list();
      log.info("Found {} contacts", contacts.size());
      return contacts;
    }
  }

  public List<Contact> findByName(String name) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      log.debug("Search contacts by name: {}", name);
      Query<Contact> namedQuery = session.getNamedQuery("Contact.findByName");
      namedQuery.setParameter("name", name);
      List<Contact> contacts = namedQuery.list();
      log.info("Found {} contacts with name {}", contacts.size(), name);
      return contacts;
    }
  }

  public List<Contact> findContactWithEmails() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      log.debug("Search for contacts with emails");
      List<Contact> list =
          session
              .createQuery(
                  "SELECT DISTINCT c FROM Contact c WHERE c.emails IS NOT EMPTY", Contact.class)
              .list();
      log.info("Found {} contacts whit emails", list.size());
      return list;
    }
  }
}
