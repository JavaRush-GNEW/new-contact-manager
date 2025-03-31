package ua.com.javarush.gnew.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateHelper {

    // For operations that return a result (e.g., queries)
    public static <T> T execute(Function<Session, T> function) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return function.apply(session);
        }
    }

    // For operations that modify data (e.g., insert, update, delete)
    public static void executeInTransaction(Consumer<Session> consumer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e; // rethrow or handle as needed
        }
    }
}
