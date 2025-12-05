package com.empresa.biblioteca.dto;

import java.time.LocalDate;

// clase para agregar un nuevo libro
// a diferencia de BookDTO, espera el id del autor y de la categoria del libro
public class PostBookDTO {

    private String isbn;

    private String title;

    private String publisher;

    private LocalDate publishedDate;

    private Integer availableCopies;

    private Integer totalCopies;

    Long authorId;

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

    public PostBookDTO() {}

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

