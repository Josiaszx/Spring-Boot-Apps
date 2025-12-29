package com.app.supermercado.service;

import com.app.supermercado.dto.ProductoMasVendido;
import com.app.supermercado.model.Producto;
import com.app.supermercado.repository.DetalleVentaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EstadisticaService {

    private DetalleVentaRepository detalleVentaRepository;

    public EstadisticaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    // producto mas vendido
    public ProductoMasVendido productoMasVendido() {

        var request = PageRequest.of(0, 1);

        var producto = detalleVentaRepository.findProductoMasVendido(request).get(0);

        var estadistica = ProductoMasVendido.builder()
                .productoId(producto.getId())
                .nombre(producto.getNombre())
                .categoria(producto.getCategoria())
                .TotalVendido(detalleVentaRepository.totalVentasPorProducto(producto.getId()))
                .build();

        return estadistica;
    }
}
