package com.app.supermercado.model;

import com.app.supermercado.dto.ProductoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    private Integer precio;
    private Integer cantidad;

    public Producto(ProductoDTO productoDTO) {
        this.nombre = productoDTO.getNombre();
        this.categoria = productoDTO.getCategoria();
        this.precio = productoDTO.getPrecio();
        this.cantidad = productoDTO.getCantidad();
    }
}
