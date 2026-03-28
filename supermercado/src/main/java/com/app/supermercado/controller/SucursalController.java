package com.app.supermercado.controller;

import com.app.supermercado.dto.SucursalDTO;
import com.app.supermercado.model.Sucursal;
import com.app.supermercado.service.SucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    final private SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public List<Sucursal> listar() {
        return sucursalService.listar();
    }

    @PostMapping
    public Sucursal registrar(@RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.registrar(sucursalDTO);
    }

    @PutMapping("/{id}")
    public Sucursal actualizar(@PathVariable Long id, @RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.actualizar(id, sucursalDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        sucursalService.eliminar(id);
    }
}