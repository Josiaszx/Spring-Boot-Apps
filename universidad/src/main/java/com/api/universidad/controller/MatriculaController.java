package com.api.universidad.controller;

import com.api.universidad.dto.MatriculaDTO;
import com.api.universidad.service.MatriculaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    /**
     * Listar todas las matrículas con paginación
     */
    @GetMapping
    public ResponseEntity<Page<MatriculaDTO>> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(matriculaService.listarTodas(pageable));
    }

    /**
     * Obtener una matrícula por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.obtenerPorId(id));
    }

    /**
     * Obtener las matrículas de un estudiante específico
     */
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(matriculaService.listarPorEstudiante(estudianteId));
    }

    /**
     * Obtener las matrículas de un curso específico
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(matriculaService.listarPorCurso(cursoId));
    }

    /**
     * Crear una nueva matrícula
     */
    @PostMapping
    public ResponseEntity<MatriculaDTO> crear(@RequestBody MatriculaDTO matriculaDTO) {
        return new ResponseEntity<>(matriculaService.crear(matriculaDTO), HttpStatus.CREATED);
    }

    /**
     * Actualizar una matrícula existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> actualizar(@PathVariable Long id, @RequestBody MatriculaDTO matriculaDTO) {
        return ResponseEntity.ok(matriculaService.actualizar(id, matriculaDTO));
    }

    /**
     * Registrar la nota final de una matrícula
     */
    @PatchMapping("/{id}/nota")
    public ResponseEntity<MatriculaDTO> registrarNota(@PathVariable Long id, @RequestBody Map<String, Double> payload) {
        Double nota = payload.get("nota");
        return ResponseEntity.ok(matriculaService.registrarNota(id, nota));
    }

    /**
     * Registrar el número de asistencias de una matrícula
     */
    @PatchMapping("/{id}/asistencia")
    public ResponseEntity<MatriculaDTO> registrarAsistencia(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Integer asistencias = payload.get("asistencia");
        return ResponseEntity.ok(matriculaService.registrarAsistencia(id, asistencias));
    }

    /**
     * Eliminar una matrícula
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        matriculaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
