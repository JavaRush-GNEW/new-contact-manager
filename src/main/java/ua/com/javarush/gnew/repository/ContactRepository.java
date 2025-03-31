package ua.com.javarush.gnew.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.util.HibernateHelper;
import ua.com.javarush.gnew.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class ContactRepository {


    public Optional<Contact> get(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Contact contact = session.get(Contact.class, id);

            return Optional.ofNullable(contact);
        }
    }

    public List<Contact> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Contact> query = session.createQuery("from Contact");
            return query.list();
        }
    }

    public void save(Contact contact) {
        HibernateHelper.executeInTransaction(session -> session.persist(contact));
    }


    public void update(Contact contact) {
        HibernateHelper.executeInTransaction(session -> session.merge(contact));
    }

    public void remove(Contact contact) {
        HibernateHelper.executeInTransaction(session -> session.remove(contact));
    }

    public void removeById(int id) {
        HibernateHelper.executeInTransaction(session -> {
            Contact contact = session.get(Contact.class, id);
            if (contact != null) {
                session.remove(contact);
            } else {
                // Logger
            }
        });
    }


    public List<Contact> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Contact> namedQuery = session.getNamedQuery("Contact.findByName");
            namedQuery.setParameter("name", name);

            return namedQuery.list();
        }
    }


    public List<Contact> findContactWithEmails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Contact> list = session.createQuery("SELECT DISTINCT c FROM Contact c WHERE c.emails IS NOT EMPTY", Contact.class).list();
            return list;
        }
    }

}
