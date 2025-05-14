package ua.com.javarush.gnew.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.com.javarush.gnew.util.HibernateUtil;

@Slf4j
public abstract class BaseRepository<T, ID extends Serializable> {

  private final Class<T> entityClass;

  public BaseRepository(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  public Optional<T> find(ID id) {
    log.debug("Finding entity {} with ID {}", entityClass.getSimpleName(), id);
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      T entity = session.find(entityClass, id);

      return Optional.ofNullable(entity);
    }
  }

  public T get(ID id) {
    log.debug("Geting entity {} with ID {}", entityClass.getSimpleName(), id);
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.get(entityClass, id);
    }
  }

  public void save(T entity) {
    log.info("Saving entity: {}", entity);
    executeInTransaction(session -> session.persist(entity));
  }

  public void update(T entity) {
    log.info("Updating entity: {}", entity);
    executeInTransaction(session -> session.merge(entity));
  }

  public void remove(T entity) {
    log.info("Removing entity: {}", entity);
    executeInTransaction(session -> session.remove(entity));
  }

  public void removeById(ID id) {
    executeInTransaction(
        session -> {
          T contact = session.get(entityClass, id);
          if (contact != null) {
            session.remove(contact);
            log.info("Removed entity: {} ID {}", entityClass.getSimpleName(), id);
          } else {
            log.warn("Entity {} ID {} not found", entityClass.getSimpleName(), id);
          }
        });
  }

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
      log.debug("Opening a new session and starting a transaction");
      transaction = session.beginTransaction();

      consumer.accept(session);

      transaction.commit();
      log.debug("The transaction was completed successfully");
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
        log.error("The transaction was rolled back due to an error");
      }
      log.error("Error while executing transaction: {}", e.getMessage(), e);
      throw e; // rethrow or handle as needed
    }
  }
}
