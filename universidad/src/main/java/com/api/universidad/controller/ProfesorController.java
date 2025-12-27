package com.api.universidad.controller;

import com.api.universidad.dto.CursoDTO;
import com.api.universidad.dto.ProfesorDTO;
import com.api.universidad.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService profesorService;

    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(profesorService.listarTodos(pageable).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.obtenerPorId(id));
    }

    @GetMapping("/departamento/{deptoId}")
    public ResponseEntity<List<ProfesorDTO>> listarPorDepartamento(@PathVariable Long deptoId) {
        return ResponseEntity.ok(profesorService.listarPorDepartamento(deptoId));
    }

    @PostMapping
    public ResponseEntity<ProfesorDTO> crear(@RequestBody ProfesorDTO profesorDTO) {
        return new ResponseEntity<>(profesorService.crear(profesorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDTO> actualizar(@PathVariable Long id, @RequestBody ProfesorDTO profesorDTO) {
        return ResponseEntity.ok(profesorService.actualizar(id, profesorDTO));
    }

    @PutMapping("/{id}/departamento")
    public ResponseEntity<ProfesorDTO> asignarDepartamento(@PathVariable Long id, @RequestBody Long deptoId) {
        return ResponseEntity.ok(profesorService.asignarDepartamento(id, deptoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        profesorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/cursos")
    public ResponseEntity<List<CursoDTO>> obtenerCursos(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.obtenerCursos(id));
    }
}
