package com.api.universidad.controller;

import com.api.universidad.dto.DepartamentoDTO;
import com.api.universidad.dto.ProfesorDTO;
import com.api.universidad.model.Departamento;
import com.api.universidad.service.DepartamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public List<DepartamentoDTO> findAll() {
        return departamentoService.findAll();
    }

    @GetMapping("/{id}")
    public DepartamentoDTO findById(@PathVariable Long id) {
        return departamentoService.findById(id);
    }

    @GetMapping("/{id}/profesores")
    public List<ProfesorDTO> listarProfesoresPorDepartamento(@PathVariable Long id) {
        return departamentoService.listarProfesoresPorDepartamento(id);
    }

    @PostMapping
    public DepartamentoDTO save(@RequestBody Departamento departamento) {
        return departamentoService.save(departamento);
    }

    @PutMapping("/{id}")
    public DepartamentoDTO update(Departamento departamento, Long id) {
        return departamentoService.update(id, departamento);
    }

    @DeleteMapping
    public void delete(Long id) {
        departamentoService.delete(id);
    }

}
