package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "orders")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    // Getters and Setters
}
