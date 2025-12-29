package com.app.supermercado.dto;

import com.app.supermercado.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoDTO {

    private String nombre;
    private String categoria;
    private Integer precio;
    private Integer cantidad;

    public ProductoDTO(Producto producto) {
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria();
        this.precio = producto.getPrecio();
        this.cantidad = producto.getCantidad();
    }
}
