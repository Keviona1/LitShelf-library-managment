package org.example;


import org.example.config.HibernateConfiguration;

import org.example.entity.Book;
import org.example.entity.Purchase;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static User loggedUser;

    public static void main(String[] args) {
        System.out.println("=== Welcome to LitShelf ===");
        login();
        if (loggedUser.getRole().equalsIgnoreCase("ADMIN")) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        Session session = HibernateConfiguration.getSessionFactory().openSession();
        loggedUser = session.createQuery("FROM User WHERE username = :username AND password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();

        session.close();

        if (loggedUser == null) {
            System.out.println("Invalid credentials. Try again.");
            login();
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> updateBook();
                case 3 -> deleteBook();
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void addBook() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Book title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setQuantity(quantity);

        session.persist(book);
        tx.commit();
        session.close();

        System.out.println("Book added successfully.");
    }

    private static void updateBook() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Book ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        Book book = session.get(Book.class, id);
        if (book == null) {
            System.out.println("Book not found.");
        } else {
            System.out.print("New title: ");
            book.setTitle(sc.nextLine());
            System.out.print("New author: ");
            book.setAuthor(sc.nextLine());
            System.out.print("New quantity: ");
            book.setQuantity(sc.nextInt());
            sc.nextLine();

            session.update(book);
            tx.commit();
            System.out.println("Book updated.");
        }

        session.close();
    }

    private static void deleteBook() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Book ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        Book book = session.get(Book.class, id);
        if (book != null) {
            session.delete(book);
            tx.commit();
            System.out.println("Book deleted.");
        } else {
            System.out.println("Book not found.");
        }

        session.close();
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Books");
            System.out.println("2. Order Book");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> orderBook();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void viewBooks() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        List<Book> books = session.createQuery("FROM Book", Book.class).list();
        System.out.println("\nAvailable Books:");
        for (Book b : books) {
            System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() + " (Quantity: " + b.getQuantity() + ")");
        }
        session.close();
    }

    private static void orderBook() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Enter Book ID to order: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        Book book = session.get(Book.class, bookId);
        if (book == null || book.getQuantity() <= 0) {
            System.out.println("Book not available.");
        } else {
            Purchase order = new Purchase();
            order.setBook(book);
            order.setUser(loggedUser);
            order.setOrderDate(new java.util.Date());

            book.setQuantity(book.getQuantity() - 1);
            session.persist(order);
            session.update(book);

            tx.commit();
            System.out.println("Book ordered successfully!");
        }

        session.close();
    }
}
