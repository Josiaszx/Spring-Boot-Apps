package com.app.supermercado.repository;

import com.app.supermercado.model.DetalleVenta;
import com.app.supermercado.model.Producto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("SELECT dv.producto " +
            "FROM DetalleVenta dv " +
            "GROUP BY dv.producto " +
            "ORDER BY SUM(dv.cantidad) DESC")
    List<Producto> findProductoMasVendido(Pageable pageable);

    @Query("SELECT SUM(dv.cantidad) FROM DetalleVenta dv WHERE dv.producto.id = :id")
    Integer totalVentasPorProducto(Long id);

}
