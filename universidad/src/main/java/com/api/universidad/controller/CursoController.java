package com.api.universidad.controller;

import com.api.universidad.dto.AsignaturaDTO;
import com.api.universidad.dto.CursoDTO;
import com.api.universidad.dto.EstudianteDTO;
import com.api.universidad.model.Curso;
import com.api.universidad.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos(
            @RequestParam(required = false) String semestre,
            @RequestParam(required = false) Long profesorId
    ) {
        return ResponseEntity.ok(cursoService.listarCursos(semestre, profesorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.obtenerCursoPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CursoDTO> obtenerCursoPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(cursoService.obtenerCursoPorCodigo(codigo));
    }

    @GetMapping("/{id}/estudiantes")
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudiantesDeCurso(@PathVariable Long id) {
        List<EstudianteDTO> estudiantes = cursoService.obtenerEstudiantesDeCurso(id).stream()
                .map(EstudianteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}/prerequisitos")
    public ResponseEntity<List<AsignaturaDTO>> obtenerPrerequisitosDeCurso(@PathVariable Long id) {
        List<AsignaturaDTO> prerequisitos = cursoService.obtenerPrerequisitosDeCurso(id).stream()
                .map(AsignaturaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prerequisitos);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<CursoDTO>> listarCursosDisponibles() {
        return ResponseEntity.ok(cursoService.listarCursosDisponibles());
    }

    @PostMapping
    public ResponseEntity<CursoDTO> crearCurso(@RequestBody Curso curso) {
        return new ResponseEntity<>(cursoService.crearCurso(curso), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.actualizarCurso(id, curso));
    }

    @PutMapping("/{id}/prerequisitos")
    public ResponseEntity<CursoDTO> asignarPrerequisitos(@PathVariable Long id, @RequestBody List<Long> asignaturaIds) {
        return ResponseEntity.ok(cursoService.asignarPrerequisitos(id, asignaturaIds));
    }

    @PatchMapping("/{id}/profesor")
    public ResponseEntity<CursoDTO> asignarProfesor(@PathVariable Long id, @RequestParam Long profesorId) {
        return ResponseEntity.ok(cursoService.asignarProfesor(id, profesorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
