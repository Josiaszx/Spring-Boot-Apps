package com.app.supermercado.service;

import com.app.supermercado.dto.VentaDTO;
import com.app.supermercado.dto.VentaPayLoad;
import com.app.supermercado.model.DetalleVenta;
import com.app.supermercado.model.Venta;
import com.app.supermercado.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    final private VentaRepository ventaRepository;
    final private SucursalService sucursalService;
    final private DetalleVentaService detalleVentaService;

    public VentaService(
            VentaRepository ventaRepository,
            SucursalService sucursalService,
            DetalleVentaService detalleVentaService
    ) {
        this.ventaRepository = ventaRepository;
        this.sucursalService = sucursalService;
        this.detalleVentaService = detalleVentaService;
    }

    public VentaDTO registrar(VentaPayLoad nuevaVenta) {
        var sucursalId = nuevaVenta.getSucursalId();
        var sucursal = sucursalService.encontrarPorId(sucursalId);
        var venta = new Venta();
        venta.setSucursal(sucursal);
        var detalleVentas = detalleVentaService.obtenerDetalleVenta(nuevaVenta);
        detalleVentas.forEach(dv -> dv.setVenta(venta));
        venta.setProductosVendidos(detalleVentas);
        var totalPagado = calcularTotalPagado(detalleVentas);
        venta.setTotal(totalPagado);
        venta.setFecha(LocalDate.now());
        venta.setEstado("FINALIZADA");
        ventaRepository.save(venta);
        var ventaDTO = new VentaDTO(venta);
        return ventaDTO;
    }

    public List<VentaDTO> listar(Long sucursalId, LocalDate fecha) {
        var sucursal = sucursalService.encontrarPorId(sucursalId);
        var ventas = ventaRepository.findAllBySucursalAndFecha(sucursal, fecha);
        return ventas.stream()
                .map(VentaDTO::new)
                .toList();
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }

    public Integer calcularTotalPagado(List<DetalleVenta> detalleVentas) {
        return detalleVentas.stream()
                .mapToInt(dv -> dv.getCantidad() * dv.getProducto().getPrecio())
                .sum();
    }
}