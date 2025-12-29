package com.app.supermercado.controller;

import com.app.supermercado.dto.SucursalDTO;
import com.app.supermercado.service.SucursalService;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    // listar todas las sucursales
    @GetMapping
    public List<SucursalDTO> listar() {
        return sucursalService.listar();
    }

    // registrar nueva sucursal
    @PostMapping
    public SucursalDTO registrar(@RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.registrar(sucursalDTO);
    }

    // actualizar sucursal
    @PutMapping("/{id}")
    public SucursalDTO actualizar(@PathVariable Long id, @RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.actualizar(id, sucursalDTO);
    }

    // eliminar sucursal
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        sucursalService.eliminar(id);
    }


}
