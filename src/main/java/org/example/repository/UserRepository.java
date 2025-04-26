package org.example.repository;

import org.example.config.HibernateConfiguration;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {
    public User findByUsernameAndPassword(String username, String password) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        User user = session.createQuery(
                        "FROM User WHERE username = :username AND password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        return user;
    }
    public void save(User user) {
        Transaction tx = null;
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}