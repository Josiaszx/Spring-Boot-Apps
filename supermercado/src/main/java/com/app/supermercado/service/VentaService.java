package com.app.supermercado.service;

import com.app.supermercado.dto.DetalleVentaDTO;
import com.app.supermercado.dto.VentaDTO;
import com.app.supermercado.dto.VentaPayLoad;
import com.app.supermercado.model.DetalleVenta;
import com.app.supermercado.model.Producto;
import com.app.supermercado.model.Sucursal;
import com.app.supermercado.model.Venta;
import com.app.supermercado.repository.ProductoRepository;
import com.app.supermercado.repository.SucursalRepository;
import com.app.supermercado.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private VentaRepository ventaRepository;
    private SucursalRepository sucursalRepository;
    private ProductoRepository productoRepository;

    public VentaService(
            VentaRepository ventaRepository,
            SucursalRepository sucursalRepository,
            ProductoRepository productoRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.sucursalRepository = sucursalRepository;
        this.productoRepository = productoRepository;
    }

    // registrar una venta
    public VentaDTO registrar(VentaPayLoad nuevaVenta) {
        var sucursalId = nuevaVenta.getSucursalId();
        var sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new IllegalArgumentException("no se encotro la sucursal con id: " + sucursalId));

        final var venta = new Venta();
        venta.setSucursal(sucursal);

        List<DetalleVenta> detalleVentas = nuevaVenta.getDetalle().stream()
                .map(detalle -> {
                    var producto = productoRepository.findById(detalle.getProductoId())
                            .orElseThrow(() -> new IllegalArgumentException("no se encotro el producto con id: " + detalle.getProductoId()));

                    var detalleVenta = new DetalleVenta();
                    detalleVenta.setProducto(producto);
                    detalleVenta.setCantidad(detalle.getCantidad());
                    detalleVenta.setVenta(venta);
                    return detalleVenta;
                })
                .toList();

        venta.setProductosVendidos(detalleVentas);

        Integer total = detalleVentas.stream()
                .mapToInt(dv -> dv.getCantidad() * dv.getProducto().getPrecio())
                .sum();

        venta.setTotal(total);
        venta.setFecha(LocalDate.now());
        venta.setEstado("FINALIZADA");

        ventaRepository.save(venta);
        var ventaDTO = new VentaDTO(venta);

        return ventaDTO;
    }

    // obtener ventas por sucursal y fecha
    public List<VentaDTO> listar(Long sucursalId, LocalDate fecha) {
        var sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro la sucursal con id: " + sucursalId));

        var ventas = ventaRepository.findAllBySucursalAndFecha(sucursal, fecha);
        return ventas.stream()
                .map(VentaDTO::new)
                .toList();
    }

    // eliminar venta por id
    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
}
