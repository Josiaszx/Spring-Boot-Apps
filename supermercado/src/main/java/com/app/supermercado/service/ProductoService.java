package com.app.supermercado.service;

import com.app.supermercado.dto.ProductoDTO;
import com.app.supermercado.model.Producto;
import com.app.supermercado.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // listar productos registrados
    public List<ProductoDTO> listarProductos() {
        var productos = productoRepository.findAll();

        return productos.stream()
                .map(ProductoDTO::new)
                .toList();
    }

    // registrar nuevo producto
    public ProductoDTO guardarProducto(ProductoDTO productoDTO) {
        var producto = new Producto(productoDTO);
        productoRepository.save(producto);
        return new ProductoDTO(producto);
    }

    // actualizar producto existente
    public ProductoDTO actualizar(ProductoDTO productoDTO, Long id) {
        var producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        producto.setCantidad(productoDTO.getCantidad());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setNombre(productoDTO.getNombre());

        producto = productoRepository.save(producto);
        return new ProductoDTO(producto);
    }

    // eliminar producto
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

}
