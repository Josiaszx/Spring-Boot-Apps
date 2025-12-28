package com.api.universidad.dto;

import com.api.universidad.model.Asignatura;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignaturaDTO {
    private String nombre;
    private String descripcion;
    private Integer creditosRequeridos;

    public AsignaturaDTO(Asignatura asignatura) {
        if (asignatura != null) {
            this.nombre = asignatura.getNombre();
            this.descripcion = asignatura.getDescripcion();
            this.creditosRequeridos = asignatura.getCreditosRequeridos();
        }
    }
}
