package com.api.universidad.service;

import com.api.universidad.dto.DepartamentoDTO;
import com.api.universidad.dto.ProfesorDTO;
import com.api.universidad.model.Departamento;
import com.api.universidad.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    // listar todos los departamentos
    public List<DepartamentoDTO> findAll() {
        var departamentos = departamentoRepository.findAll();

        var departamentosDTO = departamentos.stream()
                .map(DepartamentoDTO::new)
                .collect(Collectors.toList());

        return departamentosDTO;
    }

    // listar departamentos por id
    public DepartamentoDTO findById(Long id) {
        var departamento = departamentoRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return new DepartamentoDTO(departamento);
    }

    // obtener profesores de un departamento
    public List<ProfesorDTO> listarProfesoresPorDepartamento(Long deptoId) {
        var departamento = departamentoRepository.findById(deptoId)
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado con ID: " + deptoId));

        var profesoresDTO = departamento.getProfesores().stream()
                .map(ProfesorDTO::new)
                .collect(Collectors.toList());

        return profesoresDTO;
    }

    // crear nuevo departamento
    public DepartamentoDTO save(Departamento departamento) {
        departamento = departamentoRepository.save(departamento);
        return new DepartamentoDTO(departamento);
    }

    // actualizar departamento
    public DepartamentoDTO update(Long id, Departamento departamento) {
        departamento = departamentoRepository.save(departamento);
        return new DepartamentoDTO(departamento);
    }

    // eliminar departamento
    public void delete(Long id) {
        departamentoRepository.deleteById(id);
    }
}
