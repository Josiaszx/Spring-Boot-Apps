package com.app.veterinaria.dto;

import com.app.veterinaria.entity.User;
import com.app.veterinaria.entity.Veterinarian;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class VeterinarianDto {

    private String email;
    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;

    public VeterinarianDto(Veterinarian veterinarian, String userEmail) {
        this.firstName = veterinarian.getFirstName();
        this.lastName = veterinarian.getLastName();
        this.specialty = veterinarian.getSpecialty();
        this.licenseNumber = veterinarian.getLicenseNumber();
        this.email = userEmail;
    }
}
