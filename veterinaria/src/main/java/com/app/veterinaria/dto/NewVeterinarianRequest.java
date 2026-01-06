package com.app.veterinaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewVeterinarianRequest extends EntityRequest {

    @NotNull(message = "La especialidad no puede ser nulo")
    @NotBlank(message = "La especialidad no puede estar vacia")
    private String specialty;

    @NotNull(message = "en numero de licencia no puede ser nulo")
    @NotBlank(message = "en numero de licencia no puede estar vacio")
    private String licenseNumber;

}
