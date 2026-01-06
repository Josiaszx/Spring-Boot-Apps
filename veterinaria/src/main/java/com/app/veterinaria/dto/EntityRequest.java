package com.app.veterinaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class EntityRequest implements AnyEntityRequest {

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String username;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String firstName;

    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede estar vacio")
    private String lastName;

    @NotNull(message = "La contraseña no puede ser nula")
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;

    @NotNull(message = "El email no puede ser nulo")
    @NotBlank(message = "El email no puede estar vacio")
    private String email;

    private String role;

    private Boolean active;

    private LocalDate createdAt;
}
