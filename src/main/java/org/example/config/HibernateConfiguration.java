package org.example.config;

import lombok.Getter;
import org.example.entity.Book;
import org.example.entity.Purchase;
import org.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateConfiguration {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            try (InputStream input = HibernateConfiguration.class.getClassLoader()
                    .getResourceAsStream("hibernate.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find hibernate.properties");
                }
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load hibernate.properties", e);
            }

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Book.class);
            configuration.addAnnotatedClass(Purchase.class);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
