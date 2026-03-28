package com.app.supermercado.service;

import com.app.supermercado.dto.ProductoDTO;
import com.app.supermercado.model.Producto;
import com.app.supermercado.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    final private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto guardarProducto(ProductoDTO productoDTO) {
        return productoRepository.save(new Producto(productoDTO));
    }

    public Producto actualizar(ProductoDTO productoDTO, Long id) {
        var producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        producto.setCantidad(productoDTO.getCantidad());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setNombre(productoDTO.getNombre());

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        var existsProduct = productoRepository.existsById(id);
        if (existsProduct) productoRepository.deleteById(id);
        else throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
    }

    public Producto encontrarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }
}