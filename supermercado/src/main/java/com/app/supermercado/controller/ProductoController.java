package com.app.supermercado.controller;

import com.app.supermercado.dto.ProductoDTO;
import com.app.supermercado.model.Producto;
import com.app.supermercado.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    final private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarProductos(){
        return productoService.listarProductos();
    }

    @PostMapping
    public Producto guardarProducto(@RequestBody ProductoDTO productoDTO){
        return productoService.guardarProducto(productoDTO);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        return productoService.actualizar(productoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id){
        productoService.eliminar(id);
    }

}