package ua.com.javarush.gnew.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.util.HibernateUtil;

import java.util.List;

public class ContactRepository extends BaseRepository<Contact, Integer> {

    public ContactRepository() {
        super(Contact.class);
    }


    public List<Contact> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Contact> query = session.createQuery("from Contact");
            return query.list();
        }
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
