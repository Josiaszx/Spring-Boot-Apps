package com.app.veterinaria.entity;

import com.app.veterinaria.dto.NewPetRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String species;

    @Column(length = 50)
    private String gender;

    public Pet(NewPetRequest newPetRequest, Owner owner) {
        this.owner = owner;
        this.name = newPetRequest.getName();
        this.species = newPetRequest.getSpecies();
        this.gender = newPetRequest.getGender();
    }

    public void updatePet(Pet pet) {
        if (pet.getName() != null) this.name = pet.getName();
        if (pet.getSpecies() != null) this.species = pet.getSpecies();
        if (pet.getGender() != null) this.gender = pet.getGender();
    }
}
