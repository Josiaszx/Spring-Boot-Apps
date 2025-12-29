package com.app.supermercado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne()
    @JoinColumn(name = "venta_id")
    private Venta venta;

    private Integer cantidad;

}
