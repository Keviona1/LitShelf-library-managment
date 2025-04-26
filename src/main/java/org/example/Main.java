package org.example;

import org.example.entity.User;
import org.example.service.BookService;
import org.example.service.UserService;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final BookService bookService = new BookService();
    private static User loggedUser;

    public static void main(String[] args) {
        System.out.println("Welcome to LitShelf 📚");

        // Register or login
        authenticate();

        // App Menu after login
        if (loggedUser.getRole().equals("ADMIN")) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    private static void authenticate() {
        System.out.println("Do you have an account? (yes/no)");
        String answer = sc.nextLine().trim().toLowerCase();

        if (answer.equals("no")) {
            register();
        }

        // After registering or if already has account
        login();
    }

    private static void register() {
        System.out.println("=== Register ===");
        System.out.print("Choose username: ");
        String username = sc.nextLine();
        System.out.print("Choose password: ");
        String password = sc.nextLine();
        System.out.print("Choose role (USER or ADMIN): ");
        String role = sc.nextLine().trim().toUpperCase();

        userService.register(username, password, role);
        System.out.println("User registered successfully!\n");
    }

    private static void login() {
        System.out.println("=== Login ===");
        while (true) {
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            loggedUser = userService.login(username, password);
            if (loggedUser != null) {
                System.out.println("Login successful! Welcome, " + loggedUser.getUsername() + "!");
                break;
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. Logout");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    updateBook();
                    break;
                case "3":
                    deleteBook();
                    break;
                case "4":
                    viewBooks();
                    break;
                case "5":
                    System.out.println("Logging out...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Books");
            System.out.println("2. Order a Book");
            System.out.println("3. Logout");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewBooks();
                    break;
                case "2":
                    orderBook();
                    break;
                case "3":
                    System.out.println("Logging out...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addBook() {
        System.out.println("=== Add Book ===");
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();

        bookService.addBook(title, author, 1 );
        System.out.println("Book added successfully!");
    }

    private static void updateBook() {
        System.out.println("=== Update Book ===");
        System.out.print("Book ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Title: ");
        String title = sc.nextLine();
        System.out.print("New Author: ");
        String author = sc.nextLine();

        bookService.updateBook(id, title, author, 1);
        System.out.println("Book updated successfully!");
    }

    private static void deleteBook() {
        System.out.println("=== Delete Book ===");
        System.out.print("Book ID: ");
        int id = Integer.parseInt(sc.nextLine());

        bookService.deleteBook(id);
        System.out.println("Book deleted successfully!");
    }

    private static void viewBooks() {
        System.out.println("=== Book List ===");
        bookService.getAllBooks().forEach(book ->
                System.out.println(book.getId() + ". " + book.getTitle() + " - " + book.getAuthor())
        );
    }

    private static void orderBook() {
        System.out.println("=== Order a Book ===");
        System.out.print("Enter Book ID: ");
        int id = Integer.parseInt(sc.nextLine());
        // For now, simple ordering (no orders table yet)
        System.out.println("Book with ID " + id + " ordered successfully!");
    }
}
