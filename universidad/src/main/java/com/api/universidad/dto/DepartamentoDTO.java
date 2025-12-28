package com.api.universidad.dto;

import com.api.universidad.model.Departamento;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartamentoDTO {
    private String nombre;
    private String codigo;
    private String edificio;
    private String telefono;
    private String email;
    private List<String> nombreProfesores;

    public DepartamentoDTO(Departamento departamento) {
        this.nombre = departamento.getNombre();
        this.codigo = departamento.getCodigo();
        this.edificio = departamento.getEdificio();
        this.telefono = departamento.getTelefono();
        this.email = departamento.getEmail();
        this.nombreProfesores = departamento.getProfesores().stream()
                .map(profesor -> profesor.getNombre() + " " + profesor.getApellido())
                .collect(Collectors.toList());
    }
}
