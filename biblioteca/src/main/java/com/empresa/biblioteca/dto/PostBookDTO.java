package com.empresa.biblioteca.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// clase para agregar un nuevo libro
// a diferencia de BookDTO, espera el id del autor y de la categoria del libro
public class PostBookDTO {

    @NotNull(message = "isbn cannot be null")
    @NotEmpty(message = "isbn cannot be empty")
    private String isbn;

    @NotNull(message = "title cannot be null")
    @NotEmpty(message = "title cannot be empty")
    private String title;

    @NotNull(message = "publisher cannot be null")
    @NotEmpty(message = "publisher cannot be empty")
    private String publisher;

    @NotNull(message = "published date cannot be null")
    private LocalDate publishedDate;

    @NotNull(message = "total copies cannot be null")
    private Integer totalCopies;

    @NotNull(message = "available copies cannot be null")
    @Size(message = "available copies cannot be less than 0")
    private Integer availableCopies;

    @NotNull(message = "author id cannot be null")
    Long authorId;

    @NotNull(message = "category id cannot be null")
    Long categoryId;

    public PostBookDTO(String isbn, String title, String publisher, LocalDate publishedDate, Integer availableCopies, Integer totalCopies, Long authorId, Long categoryId) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authorId = authorId;
        this.categoryId = categoryId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

