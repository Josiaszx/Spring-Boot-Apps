package com.app.supermercado.repository;

import com.app.supermercado.model.Sucursal;
import com.app.supermercado.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findAllBySucursalAndFecha(Sucursal sucursal, LocalDate fecha);
}
