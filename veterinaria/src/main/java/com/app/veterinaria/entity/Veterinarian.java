package com.app.veterinaria.entity;

import com.app.veterinaria.dto.NewVeterinarianRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "veterinarians")
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String specialty;

    @Column(nullable = false, length = 50, unique = true)
    private String licenseNumber;

    public Veterinarian(NewVeterinarianRequest request, User user) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.specialty = request.getSpecialty();
        this.licenseNumber = request.getLicenseNumber();
        this.user = user;
    }
}
