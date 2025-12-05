package com.empresa.biblioteca.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(length = 30, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String nationality;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column
    private String biography;

    public Author(Long id, String name, String lastName, String email, String nationality, LocalDate birthDate, String biography) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.biography = biography;
    }

    public Author() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
