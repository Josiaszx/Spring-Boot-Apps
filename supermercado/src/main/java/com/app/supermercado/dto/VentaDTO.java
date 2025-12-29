package com.app.supermercado.dto;

import com.app.supermercado.model.DetalleVenta;
import com.app.supermercado.model.Sucursal;
import com.app.supermercado.model.Venta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaDTO {

    private LocalDate fecha;
    private String estado;
    private SucursalDTO sucursal;
    private List<DetalleVentaDTO> productosVendidos;
    private Integer total;

    public VentaDTO(Venta venta) {
        this.fecha = venta.getFecha();
        this.estado = venta.getEstado();
        this.sucursal = new SucursalDTO(venta.getSucursal());
        this.productosVendidos = venta.getProductosVendidos().stream()
                .map(DetalleVentaDTO::new)
                .toList();
        this.total = venta.getTotal();
    }
}
