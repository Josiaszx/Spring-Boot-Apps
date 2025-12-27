package com.api.universidad.dto;


import com.api.universidad.model.Estudiante;
import com.api.universidad.model.PerfilAcademico;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteDTO {

    private String nombre;
    private String apellido;
    private String codigo;
    private String email;
    private LocalDate fechaIngreso;
    private String carrera;
    private PerfilAcademico perfilAcademico;

    public EstudianteDTO(Estudiante estudiante) {
        this.nombre = estudiante.getNombre();
        this.apellido = estudiante.getApellido();
        this.codigo = estudiante.getCodigo();
        this.email = estudiante.getEmail();
        this.fechaIngreso = estudiante.getFechaIngreso();
        this.carrera = estudiante.getCarrera();
        this.perfilAcademico = estudiante.getPerfilAcademico();
    }


}
