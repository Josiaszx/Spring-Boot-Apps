package com.empresa.biblioteca.dto;

import com.empresa.biblioteca.model.Author;
import jakarta.validation.constraints.*;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;

public class AuthorDTO {

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @Size(max = 30, message = "name cannot be larger than 30 characters")
    private String name;

    @NotNull(message = "lastName cannot be null")
    @NotEmpty(message = "lastName cannot be empty")
    @Size(max = 30, message = "lastName cannot be larger than 30 characters")
    private String lastName;

    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "nationality cannot be null")
    @NotEmpty(message = "nationality cannot be empty")
    @Size(max = 30, message = "nationality cannot be larger than 30 characters")
    private String nationality;

    @NotNull(message = "birthDate cannot be null")
    private LocalDate birthDate;

    @NotNull(message = "biography cannot be null")
    @NotEmpty(message = "biography cannot be empty")
    @Size(max = 255, message = "biography cannot be larger than 255 characters")
    private String biography;

    public AuthorDTO(String name, String lastName, String email, String nationality, LocalDate birthDate, String biography) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.biography = biography;
    }

    public AuthorDTO(Author author) {
        this.name = author.getName();
        this.lastName = author.getLastName();
        this.email = author.getEmail();
        this.nationality = author.getNationality();
        this.birthDate = author.getBirthDate();
        this.biography = author.getBiography();
    }

    public AuthorDTO() {}

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
