package com.api.universidad.dto;

import com.api.universidad.model.Curso;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private Integer creditos;
    private Integer cuposMaximos;
    private Integer cuposDisponibles;
    private String semestre;
    private Boolean activo;
    private String nombreProfesor;

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nombre = curso.getNombre();
        this.codigo = curso.getCodigo();
        this.descripcion = curso.getDescripcion();
        this.creditos = curso.getCreditos();
        this.cuposMaximos = curso.getCuposMaximos();
        this.cuposDisponibles = curso.getCuposDisponibles();
        this.semestre = curso.getSemestre();
        this.activo = curso.getActivo();
        if (curso.getProfesor() != null) {
            this.nombreProfesor = curso.getProfesor().getNombre() + " " + curso.getProfesor().getApellido();
        }
    }
}
