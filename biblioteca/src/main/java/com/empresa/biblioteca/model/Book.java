package com.empresa.biblioteca.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(length = 40, unique = true, nullable = false)
    private String title;

    @Column(length = 40, nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Date publishedDate;

    @Column(nullable = false)
    private Integer availableCopies;

    @Column(nullable = false)
    private Integer totalCopies;

    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;


}
