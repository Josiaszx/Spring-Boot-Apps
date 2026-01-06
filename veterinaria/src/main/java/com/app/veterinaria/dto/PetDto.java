package com.app.veterinaria.dto;

import com.app.veterinaria.entity.Pet;
import lombok.Data;

@Data
public class PetDto {
    private String name;
    private String species;
    private String gender;
    private String ownerName;
    private String ownerPhoneNumber;

    public PetDto(Pet pet) {
        this.name = pet.getName();
        this.species = pet.getSpecies();
        this.gender = pet.getGender();
        this.ownerName = pet.getOwner().getName() + " " + pet.getOwner().getLastName();
        this.ownerPhoneNumber = pet.getOwner().getPhoneNumber();
    }
}
