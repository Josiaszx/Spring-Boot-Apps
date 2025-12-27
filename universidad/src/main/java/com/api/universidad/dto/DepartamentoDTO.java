package com.api.universidad.dto;

import com.api.universidad.model.Departamento;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartamentoDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String edificio;
    private String telefono;
    private String email;

    public DepartamentoDTO(Departamento departamento) {
        this.id = departamento.getId();
        this.nombre = departamento.getNombre();
        this.codigo = departamento.getCodigo();
        this.edificio = departamento.getEdificio();
        this.telefono = departamento.getTelefono();
        this.email = departamento.getEmail();
    }
}
