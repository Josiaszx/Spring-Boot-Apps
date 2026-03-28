package com.app.supermercado.controller;

import com.app.supermercado.dto.VentaDTO;
import com.app.supermercado.dto.VentaPayLoad;
import com.app.supermercado.service.VentaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    final private VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public VentaDTO registrar(@RequestBody VentaPayLoad nuevaVenta) {
        return ventaService.registrar(nuevaVenta);
    }

    @GetMapping
    public List<VentaDTO> listar(
            @RequestParam(defaultValue = "1") Long sucursalId,
            @RequestParam(defaultValue = "") LocalDate fecha
    ) {
        if (fecha == null) fecha = LocalDate.now();
        return ventaService.listar(sucursalId, fecha);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
    }
}