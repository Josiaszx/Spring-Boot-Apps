package com.empresa.biblioteca.model;

import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.dto.PostBookDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(length = 60, unique = true, nullable = false)
    private String title;

    @Column(length = 40, nullable = false)
    private String publisher;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false)
    private Integer availableCopies;

    @Column(nullable = false)
    private Integer totalCopies;

    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;


    // constructores y getters/setters
    public Book(Long id, String isbn, String title, String publisher, LocalDate publishedDate, Integer availableCopies, Integer totalCopies, Author author, Category category) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.author = author;
        this.category = category;
    }

    public Book(BookDTO bookDTO, Author author, Category category) {
        this.isbn = bookDTO.getIsbn();
        this.title = bookDTO.getTitle();
        this.publisher = bookDTO.getPublisher();
        this.publishedDate = bookDTO.getPublishedDate();
        this.availableCopies = bookDTO.getAvailableCopies();
        this.totalCopies = bookDTO.getTotalCopies();
        this.author = author;
        this.category = category;
    }

    public Book(PostBookDTO PostBookDTO, Author author, Category category) {
        this.isbn = PostBookDTO.getIsbn();
        this.title = PostBookDTO.getTitle();
        this.publisher = PostBookDTO.getPublisher();
        this.publishedDate = PostBookDTO.getPublishedDate();
        this.availableCopies = PostBookDTO.getAvailableCopies();
        this.totalCopies = PostBookDTO.getTotalCopies();
        this.author = author;
        this.category = category;
    }

    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
