package com.empresa.biblioteca.dto;

import com.empresa.biblioteca.model.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MemberDTO {

    @NotNull(message = "membership number cannot be null")
    @NotEmpty(message = "membership number cannot be empty")
    @Size(max = 40, message = "membership number cannot be larger then 40 characters")
    private String memberShipNumber;

    @NotNull(message = "first name cannot be null")
    @NotEmpty(message = "first name cannot be empty")
    @Size(max = 30, message = "first name cannot be larger then 30 characters")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @NotEmpty(message = "last name cannot be empty")
    @Size(max = 30, message = "last name cannot be larger then 30 characters")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Size(max = 50, message = "email cannot be larger then 50 characters")
    @Email
    private String email;

    @NotNull(message = "phone number cannot be null")
    @NotEmpty(message = "phone number cannot be empty")
    @Size(max = 30, message = "phone number cannot be larger then 30 characters")
    private String phone;

    @NotNull(message = "registration date cannot be null")
    private LocalDate registrationDate;

    private Boolean active;

    public MemberDTO(String memberShipNumber, String firstName, String lastName, String email, String phone, LocalDate registrationDate, Boolean active) {
        this.memberShipNumber = memberShipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.active = active == null ? true : active;
    }

    public MemberDTO(Member member) {
        this.memberShipNumber = member.getMemberShipNumber();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.email = member.getEmail();
        this.phone = member.getPhone();
        this.registrationDate = member.getRegistrationDate();
        this.active = member.isActive();
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
