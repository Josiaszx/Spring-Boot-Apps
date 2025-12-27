package com.api.universidad.dto;

import com.api.universidad.model.EstadoAcademico;
import com.api.universidad.model.NivelAcademico;
import com.api.universidad.model.PerfilAcademico;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilAcademicoDTO {

    private Double promedioGeneral;
    private Integer creditosCompletados;
    private NivelAcademico nivelAcademico;
    private EstadoAcademico estadoAcademico;
    private LocalDate fechaActualizacion;

    public PerfilAcademicoDTO(PerfilAcademico perfilAcademico) {
        this.promedioGeneral = perfilAcademico.getPromedioGeneral();
        this.creditosCompletados = perfilAcademico.getCreditosCompletados();
        this.nivelAcademico = perfilAcademico.getNivelAcademico();
        this.estadoAcademico = perfilAcademico.getEstadoAcademico();
        this.fechaActualizacion = perfilAcademico.getFechaActualizacion();
    }
}
