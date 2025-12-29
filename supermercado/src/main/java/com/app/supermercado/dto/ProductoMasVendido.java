package com.app.supermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoMasVendido {

    private Long productoId;
    private String nombre;
    private String categoria;
    private Integer TotalVendido;
}
