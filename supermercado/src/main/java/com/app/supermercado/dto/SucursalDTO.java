package com.app.supermercado.dto;

import com.app.supermercado.model.Sucursal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SucursalDTO {

    private String nombre;
    private String direccion;

    public SucursalDTO(Sucursal sucursal) {
        this.nombre = sucursal.getNombre();
        this.direccion = sucursal.getDireccion();
    }
}
