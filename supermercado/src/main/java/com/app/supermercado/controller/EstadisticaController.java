package com.app.supermercado.controller;

import com.app.supermercado.dto.ProductoMasVendido;
import com.app.supermercado.service.EstadisticaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    private EstadisticaService estadisticaService;

    public EstadisticaController(EstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    @GetMapping("/producto-mas-vendido")
    public ProductoMasVendido productoMasVendido() {
        return estadisticaService.productoMasVendido();
    }

}
