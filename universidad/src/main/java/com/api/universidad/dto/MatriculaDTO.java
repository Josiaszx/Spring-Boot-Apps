package com.api.universidad.dto;

import com.api.universidad.model.Curso;
import com.api.universidad.model.Estudiante;
import com.api.universidad.model.Matricula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatriculaDTO {

    private Long id;
    private LocalDate fechaMatricula;
    private Double notaFinal;
    private String estado;
    private Integer asistencias;
    private Long estudianteId;
    private Long cursoId;
    private String nombreEstudiante;
    private String nombreCurso;

    public MatriculaDTO(Matricula matricula) {
        this.id = matricula.getId();
        this.fechaMatricula = matricula.getFechaMatricula();
        this.notaFinal = matricula.getNotaFinal();
        this.estado = matricula.getEstado();
        this.asistencias = matricula.getAsistencias();
        if (matricula.getEstudiante() != null) {
            this.estudianteId = matricula.getEstudiante().getId();
            this.nombreEstudiante = matricula.getEstudiante().getNombre() + " " + matricula.getEstudiante().getApellido();
        }
        if (matricula.getCurso() != null) {
            this.cursoId = matricula.getCurso().getId();
            this.nombreCurso = matricula.getCurso().getNombre();
        }
    }

}
