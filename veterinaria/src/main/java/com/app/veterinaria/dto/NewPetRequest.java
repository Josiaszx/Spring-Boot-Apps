package com.app.veterinaria.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewPetRequest{

    private Long ownerId;

    @NotNull(message = "el nombre del animal no puede ser nulo")
    @NotBlank(message = "el nombre del animal no puede estar vacio")
    private String name;

    @NotNull(message = "la especie del animal no puede ser nula")
    @NotBlank(message = "la especie del animal no puede estar vacia")
    private String species;

    @NotNull(message = "el genero del animal no puede ser nulo")
    @NotBlank(message = "el genero del animal no puede estar vacio")
    private String gender;

    private String breed;

    private LocalDate birthDate;

    private Double weight;
}
