package com.api.universidad.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostEstudianteDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @NotNull(message = "El apellido no puede ser nulo")
    private String apellido;

    @NotBlank(message = "El código es obligatorio")
    @NotNull(message = "El codigo no puede ser nulo")
    private String codigo;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;

    @NotBlank(message = "La carrera es obligatoria")
    @NotNull(message = "La carrera no puede ser nula")
    private String carrera;

    // Campos de PerfilAcademico
    @NotNull(message = "El promedio general es obligatorio")
    @PositiveOrZero(message = "El promedio debe ser un valor positivo o cero")
    @Max(value = 10, message = "El promedio no puede ser mayor a 10")
    private Double promedioGeneral;

    @NotNull(message = "Los créditos completados son obligatorios")
    @PositiveOrZero(message = "Los créditos deben ser un valor positivo o cero")
    private Integer creditosCompletados;

    private String nivelAcademico;

    private String estadoAcademico;

    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDate fechaActualizacion;
}
