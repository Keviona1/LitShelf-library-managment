package org.example.repository;

import org.example.config.HibernateConfiguration;
import org.example.entity.Purchase;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PurchaseRepository {
    public void save(Purchase purchase) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(purchase);
        tx.commit();
        session.close();
    }
}