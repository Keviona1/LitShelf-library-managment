package org.example.service;

import org.example.entity.Book;
import org.example.repository.BookRepository;

import java.util.List;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();

    public void addBook(String title, String author, int quantity) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setQuantity(quantity);
        bookRepository.save(book);
    }

    public void updateBook(int id, String title, String author, int quantity) {
        Book book = bookRepository.findById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setQuantity(quantity);
            bookRepository.update(book);
        }
    }

    public void deleteBook(int id) {
        Book book = bookRepository.findById(id);
        if (book != null) {
            bookRepository.delete(book);
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }
}
