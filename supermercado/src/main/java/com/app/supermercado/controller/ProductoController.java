package com.app.supermercado.controller;

import com.app.supermercado.dto.ProductoDTO;
import com.app.supermercado.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // listar productos registrados
    @GetMapping
    public List<ProductoDTO> listarProductos(){
        return productoService.listarProductos();
    }

    // registrar nuevo producto
    @PostMapping
    public ProductoDTO guardarProducto(@RequestBody ProductoDTO productoDTO){
        return productoService.guardarProducto(productoDTO);
    }

    // actualizar producto existente
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        return productoService.actualizar(productoDTO, id);
    }

    // eliminar producto existente
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id){
        productoService.eliminar(id);
    }

}
