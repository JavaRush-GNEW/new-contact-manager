package ua.com.javarush.gnew.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.javarush.gnew.entity.Contact;
import ua.com.javarush.gnew.entity.Email;
import ua.com.javarush.gnew.entity.Phone;
import ua.com.javarush.gnew.entity.SocialNetwork;

import java.util.Properties;

@Slf4j
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties hibernate = new Properties();

            hibernate.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            hibernate.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            // Environment variables for configuration
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USERNAME");
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new RuntimeException("Database environment variables are not properly set.");
            }

            hibernate.setProperty("hibernate.connection.url", dbUrl);
            hibernate.setProperty("hibernate.connection.username", dbUser);
            hibernate.setProperty("hibernate.connection.password", dbPassword);

            // hibernate.setProperty("hibernate.show_sql", "true");
            // hibernate.setProperty("hibernate.format_sql", "true");
            hibernate.setProperty("hibernate.hbm2ddl.auto", "update");

            Configuration configuration = new Configuration();
            configuration.addProperties(hibernate);

            configuration.addAnnotatedClass(Contact.class);
            configuration.addAnnotatedClass(SocialNetwork.class);
            configuration.addAnnotatedClass(Email.class);
            configuration.addAnnotatedClass(Phone.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
