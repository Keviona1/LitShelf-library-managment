package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
@Entity(name = "books")
@Data
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private int yearOfRelease;
    private String authorName;
    private double price;
    private int pages;

    @ManyToOne
    private Genre genre;



}