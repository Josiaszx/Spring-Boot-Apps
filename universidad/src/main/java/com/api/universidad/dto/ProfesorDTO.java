package com.api.universidad.dto;

import com.api.universidad.model.Profesor;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorDTO {
    private String nombre;
    private String apellido;
    private String codigo;
    private String email;
    private String especialidad;
    private LocalDate fechaContratacion;
    private String nombreDepartamento;

    public ProfesorDTO(Profesor profesor) {
        this.nombre = profesor.getNombre();
        this.apellido = profesor.getApellido();
        this.codigo = profesor.getCodigo();
        this.email = profesor.getEmail();
        this.especialidad = profesor.getEspecialidad();
        this.fechaContratacion = profesor.getFechaContratacion();
        if (profesor.getDepartamento() != null) {
            this.nombreDepartamento = profesor.getDepartamento().getNombre();
        }
    }
}
