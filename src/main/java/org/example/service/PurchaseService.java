package org.example.service;

import org.example.entity.Book;
import org.example.entity.Purchase;
import org.example.entity.User;
import org.example.repository.BookRepository;
import org.example.repository.PurchaseRepository;

import java.util.Date;

public class PurchaseService {
    private final BookRepository bookRepository = new BookRepository();
    private final PurchaseRepository purchaseRepository = new PurchaseRepository();

    public boolean orderBook(User user, int bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null || book.getQuantity() <= 0) {
            return false;
        }
        Purchase purchase = new Purchase();
        purchase.setBook(book);
        purchase.setUser(user);
        purchase.setOrderDate(new Date());
        book.setQuantity(book.getQuantity() - 1);
        purchaseRepository.save(purchase);
        bookRepository.update(book);
        return true;
    }
}
