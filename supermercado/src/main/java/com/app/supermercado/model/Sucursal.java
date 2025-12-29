package com.app.supermercado.model;

import com.app.supermercado.dto.SucursalDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sucursales")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;

    public Sucursal(SucursalDTO sucursalDTO) {
        this.nombre = sucursalDTO.getNombre();
        this.direccion = sucursalDTO.getDireccion();
    }

}
