package com.api.universidad.service;

import com.api.universidad.dto.CursoDTO;
import com.api.universidad.exception.BusinessException;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Asignatura;
import com.api.universidad.model.Curso;
import com.api.universidad.model.Estudiante;
import com.api.universidad.model.Profesor;
import com.api.universidad.repository.AsignaturaRepository;
import com.api.universidad.repository.CursoRepository;
import com.api.universidad.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ProfesorRepository profesorRepository;
    private final AsignaturaRepository asignaturaRepository;

    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursos(String semestre, Long profesorId) {
        List<Curso> cursos = cursoRepository.findAll();
        
        if (semestre != null) {
            cursos = cursos.stream()
                    .filter(c -> c.getSemestre().equalsIgnoreCase(semestre))
                    .collect(Collectors.toList());
        }
        
        if (profesorId != null) {
            cursos = cursos.stream()
                    .filter(c -> c.getProfesor() != null && c.getProfesor().getId().equals(profesorId))
                    .collect(Collectors.toList());
        }
        
        return cursos.stream().map(CursoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CursoDTO obtenerCursoPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return new CursoDTO(curso);
    }

    @Transactional(readOnly = true)
    public CursoDTO obtenerCursoPorCodigo(String codigo) {
        Curso curso = cursoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con código: " + codigo));
        return new CursoDTO(curso);
    }

    @Transactional(readOnly = true)
    public List<Estudiante> obtenerEstudiantesDeCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return curso.getMatriculas().stream()
                .map(m -> m.getEstudiante())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Asignatura> obtenerPrerequisitosDeCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return curso.getPrerequisitos();
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursosDisponibles() {
        return cursoRepository.findAll().stream()
                .filter(c -> c.getCuposDisponibles() > 0 && c.getActivo())
                .map(CursoDTO::new)
                .collect(Collectors.toList());
    }

    public CursoDTO crearCurso(Curso curso) {
        if (cursoRepository.findByCodigo(curso.getCodigo()).isPresent()) {
            throw new BusinessException("El código del curso ya existe: " + curso.getCodigo());
        }
        // Inicializar cupos disponibles si no se proporcionan
        if (curso.getCuposDisponibles() == null) {
            curso.setCuposDisponibles(curso.getCuposMaximos());
        }
        Curso nuevoCurso = cursoRepository.save(curso);
        return new CursoDTO(nuevoCurso);
    }

    public CursoDTO actualizarCurso(Long id, Curso cursoDetalles) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        
        curso.setNombre(cursoDetalles.getNombre());
        curso.setDescripcion(cursoDetalles.getDescripcion());
        curso.setCreditos(cursoDetalles.getCreditos());
        curso.setCuposMaximos(cursoDetalles.getCuposMaximos());
        curso.setSemestre(cursoDetalles.getSemestre());
        curso.setActivo(cursoDetalles.getActivo());
        
        // Ajustar cupos disponibles si cambió el máximo (lógica simplificada)
        // En un caso real, esto requeriría contar matrículas activas
        
        return new CursoDTO(cursoRepository.save(curso));
    }

    public CursoDTO asignarPrerequisitos(Long id, List<Long> asignaturaIds) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        
        List<Asignatura> prerequisitos = asignaturaRepository.findAllById(asignaturaIds);
        curso.setPrerequisitos(prerequisitos);
        
        return new CursoDTO(cursoRepository.save(curso));
    }

    public CursoDTO asignarProfesor(Long id, Long profesorId) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + profesorId));
        
        // Regla de negocio: No permitir que un profesor tenga más de 5 cursos activos simultáneamente
        long cursosActivos = profesor.getCursos().stream()
                .filter(c -> c.getActivo())
                .count();
        
        if (cursosActivos >= 5) {
            throw new BusinessException("El profesor ya tiene el máximo de 5 cursos activos");
        }
        
        curso.setProfesor(profesor);
        return new CursoDTO(cursoRepository.save(curso));
    }

    public void eliminarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        
        if (!curso.getMatriculas().isEmpty()) {
            throw new BusinessException("No se puede eliminar un curso que tiene estudiantes matriculados");
        }
        
        cursoRepository.delete(curso);
    }
}
