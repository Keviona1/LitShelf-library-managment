package org.example;

import org.example.entity.Book;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.BookService;
import org.example.service.PurchaseService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static User loggedUser;
    private static final BookService bookService = new BookService();
    private static final PurchaseService purchaseService = new PurchaseService();

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
        UserRepository userRepository = new UserRepository();
        while (true) {
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();
            loggedUser = userRepository.findByUsernameAndPassword(username, password);
            if (loggedUser != null) break;
            System.out.println("Invalid credentials. Try again.");
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
            sc.nextLine();
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
        System.out.print("Book title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        bookService.addBook(title, author, quantity);
        System.out.println("Book added successfully.");
    }

    private static void updateBook() {
        System.out.print("Book ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("New title: ");
        String title = sc.nextLine();
        System.out.print("New author: ");
        String author = sc.nextLine();
        System.out.print("New quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        bookService.updateBook(id, title, author, quantity);
        System.out.println("Book updated.");
    }

    private static void deleteBook() {
        System.out.print("Book ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();
        bookService.deleteBook(id);
        System.out.println("Book deleted.");
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
        List<Book> books = bookService.getAllBooks();
        System.out.println("\nAvailable Books:");
        for (Book b : books) {
            System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() + " (Quantity: " + b.getQuantity() + ")");
        }
    }

    private static void orderBook() {
        System.out.print("Enter Book ID to order: ");
        int bookId = sc.nextInt();
        sc.nextLine();
        if (purchaseService.orderBook(loggedUser, bookId)) {
            System.out.println("Book ordered successfully!");
        } else {
            System.out.println("Book not available.");
        }
    }
}
