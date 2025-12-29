package com.app.supermercado.dto;

import com.app.supermercado.model.DetalleVenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleVentaDTO {

    private String producto;
    private Integer precio;
    private Integer cantidad;
    private Integer precioTotal;

    public DetalleVentaDTO(DetalleVenta detalleVenta) {
        this.producto = detalleVenta.getProducto().getNombre();
        this.precio = detalleVenta.getProducto().getPrecio();
        this.cantidad = detalleVenta.getCantidad();
        this.precioTotal = detalleVenta.getCantidad() * detalleVenta.getProducto().getPrecio();
    }
}
