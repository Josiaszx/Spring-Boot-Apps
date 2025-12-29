package com.app.supermercado.service;

import com.app.supermercado.dto.SucursalDTO;
import com.app.supermercado.model.Sucursal;
import com.app.supermercado.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    private SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    // listar todas las sucursales
    public List<SucursalDTO> listar() {
        var sucursales = sucursalRepository.findAll();
        return sucursales.stream()
                .map(SucursalDTO::new)
                .toList();
    }

    // registrar nueva sucursal
    public SucursalDTO registrar(SucursalDTO sucursalDTO) {
        var sucursal = sucursalRepository.save(new Sucursal(sucursalDTO));
        return new SucursalDTO(sucursal);
    }

    // actualizar sucursal
    public SucursalDTO actualizar(Long id, SucursalDTO sucursalDTO) {
        var sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada con ID: " + id));

        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setDireccion(sucursalDTO.getDireccion());

        sucursal = sucursalRepository.save(sucursal);
        return new SucursalDTO(sucursal);
    }

    // eliminar sucursal
    public void eliminar(Long id) {
        sucursalRepository.deleteById(id);
    }

}
