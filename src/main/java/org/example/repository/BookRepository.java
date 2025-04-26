package org.example.repository;

import org.example.config.HibernateConfiguration;
import org.example.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookRepository {

    public void save(Book book) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(book);
        tx.commit();
        session.close();
    }

    public Book findById(int id) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Book book = session.get(Book.class, id);
        session.close();
        return book;
    }

    public void update(Book book) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(book);
        tx.commit();
        session.close();
    }

    public void delete(Book book) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(book);
        tx.commit();
        session.close();
    }

    public List<Book> findAll() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        List<Book> books = session.createQuery("FROM Book", Book.class).list();
        session.close();
        return books;
    }
}
