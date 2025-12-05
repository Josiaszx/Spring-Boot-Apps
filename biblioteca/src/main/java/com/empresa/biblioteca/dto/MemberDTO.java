package com.empresa.biblioteca.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class MemberDTO {

    private String memberShipNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate registrationDate;

    private Boolean active;

    public MemberDTO(String memberShipNumber, String firstName, String lastName, String email, String phone, LocalDate registrationDate, Boolean active) {
        this.memberShipNumber = memberShipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.active = active == null || active;
    }

    public MemberDTO() {}

    public String getMemberShipNumber() {
        return memberShipNumber;
    }

    public void setMemberShipNumber(String memberShipNumber) {
        this.memberShipNumber = memberShipNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
