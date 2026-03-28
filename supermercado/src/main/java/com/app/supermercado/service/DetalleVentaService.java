package com.app.supermercado.service;

import com.app.supermercado.dto.VentaPayLoad;
import com.app.supermercado.model.DetalleVenta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService {

    final private ProductoService productoService;

    public DetalleVentaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public List<DetalleVenta> obtenerDetalleVenta(VentaPayLoad ventaPayLoad) {
        return ventaPayLoad.getDetalle().stream()
                .map(detalle -> {
                    var producto = productoService.encontrarPorId(detalle.getProductoId());
                    var detalleVenta = new DetalleVenta();
                    detalleVenta.setProducto(producto);
                    detalleVenta.setCantidad(detalle.getCantidad());
                    return detalleVenta;
                })
                .toList();
    }
}