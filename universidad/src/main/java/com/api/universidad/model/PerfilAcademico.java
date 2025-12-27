package com.api.universidad.model;

import jakarta.persistence.*;
import lombok.*;
import com.api.universidad.dto.PostEstudianteDTO;

import java.time.LocalDate;

@Entity
@Table(name = "perfiles_academicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promedio_general")
    private Double promedioGeneral;

    @Column(name = "creditos_completados")
    private Integer creditosCompletados;

    @Column(name = "nivel_academico")
    @Enumerated(EnumType.STRING)
    private NivelAcademico nivelAcademico;

    @Column(name = "estado_academico")
    @Enumerated(EnumType.STRING)
    private EstadoAcademico estadoAcademico;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    public PerfilAcademico(PostEstudianteDTO dto) {
        this.promedioGeneral = dto.getPromedioGeneral();
        this.creditosCompletados = dto.getCreditosCompletados();
        this.nivelAcademico = dto.getNivelAcademico() == null ? NivelAcademico.PRIMERO : NivelAcademico.valueOf(dto.getNivelAcademico());
        this.estadoAcademico = dto.getEstadoAcademico() == null ? EstadoAcademico.ACTIVO : EstadoAcademico.valueOf(dto.getEstadoAcademico());
        this.fechaActualizacion = dto.getFechaActualizacion();
    }

}
