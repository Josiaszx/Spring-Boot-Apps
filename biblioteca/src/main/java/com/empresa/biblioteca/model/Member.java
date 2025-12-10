package com.empresa.biblioteca.model;

import com.empresa.biblioteca.dto.MemberDTO;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 40)
    private String memberShipNumber;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, updatable = false)
    private LocalDate registrationDate;

    @Column()
    private boolean active;

    public Member(Long id, String memberShipNumber, String firstName, String lastName, String email, String phone, LocalDate registrationDate, boolean active) {
        this.id = id;
        this.memberShipNumber = memberShipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.active = active;
    }

    public Member(MemberDTO memberDTO) {
        this.memberShipNumber = memberDTO.getMemberShipNumber();
        this.firstName = memberDTO.getFirstName();
        this.lastName = memberDTO.getLastName();
        this.email = memberDTO.getEmail();
        this.phone = memberDTO.getPhone();
        this.registrationDate = memberDTO.getRegistrationDate();
        this.active = memberDTO.isActive();
    }

    public Member() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
