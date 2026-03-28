package com.app.supermercado.service;

import com.app.supermercado.dto.SucursalDTO;
import com.app.supermercado.model.Sucursal;
import com.app.supermercado.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    final private SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public List<Sucursal> listar() {
        return sucursalRepository.findAll();
    }

    public Sucursal registrar(SucursalDTO sucursalDTO) {
        return sucursalRepository.save(new Sucursal(sucursalDTO));
    }

    public Sucursal actualizar(Long id, SucursalDTO sucursalDTO) {
        var sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada con ID: " + id));
        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setDireccion(sucursalDTO.getDireccion());
        return sucursalRepository.save(sucursal);
    }

    public void eliminar(Long id) {
        sucursalRepository.deleteById(id);
    }

    public Sucursal encontrarPorId(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada con ID: " + id));
    }
}