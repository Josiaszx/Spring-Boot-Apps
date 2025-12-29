package com.app.supermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleProductoCantidad {

    private Long productoId;
    private Integer cantidad;
}
