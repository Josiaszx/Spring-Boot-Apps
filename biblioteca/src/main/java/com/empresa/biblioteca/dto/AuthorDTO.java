package com.empresa.biblioteca.dto;

import java.util.Date;

public class AuthorDTO {

    private String name;

    private String lastName;

    private String email;

    private String nationality;

    private Date birthDate;

    private String biography;

    public AuthorDTO(String name, String lastName, String email, String nationality, Date birthDate, String biography) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.biography = biography;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
