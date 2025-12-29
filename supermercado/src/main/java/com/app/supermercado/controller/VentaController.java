package com.app.supermercado.controller;

import com.app.supermercado.dto.VentaDTO;
import com.app.supermercado.dto.VentaPayLoad;
import com.app.supermercado.service.VentaService;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // registrar una venta
    @PostMapping
    public VentaDTO registrar(@RequestBody VentaPayLoad nuevaVenta) {
        return ventaService.registrar(nuevaVenta);
    }

    // obtener venta por sucursal y por fecha
    @GetMapping
    public List<VentaDTO> listar(
            @RequestParam(defaultValue = "1") Long sucursalId,
            @RequestParam(defaultValue = "2025-12-28") LocalDate fecha
    ) {
        return ventaService.listar(sucursalId, fecha);
    }

    // eliminar venta
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
    }


}
