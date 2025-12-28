package com.api.universidad.controller;

import com.api.universidad.dto.AsignaturaDTO;
import com.api.universidad.dto.CursoDTO;
import com.api.universidad.service.AsignaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
@RequiredArgsConstructor
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> listarTodas() {
        return ResponseEntity.ok(asignaturaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asignaturaService.obtenerPorId(id));
    }

    @GetMapping("/{id}/cursos")
    public ResponseEntity<List<CursoDTO>> obtenerCursosPorAsignatura(@PathVariable Long id) {
        return ResponseEntity.ok(asignaturaService.obtenerCursosPorAsignatura(id));
    }

    @PostMapping
    public ResponseEntity<AsignaturaDTO> crear(@Valid @RequestBody AsignaturaDTO dto) {
        return new ResponseEntity<>(asignaturaService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AsignaturaDTO dto) {
        return ResponseEntity.ok(asignaturaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asignaturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
