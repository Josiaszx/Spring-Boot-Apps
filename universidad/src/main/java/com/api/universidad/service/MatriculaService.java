package com.api.universidad.service;

import com.api.universidad.dto.MatriculaDTO;
import com.api.universidad.exception.BusinessException;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Asignatura;
import com.api.universidad.model.Curso;
import com.api.universidad.model.Estudiante;
import com.api.universidad.model.Matricula;
import com.api.universidad.repository.CursoRepository;
import com.api.universidad.repository.EstudianteRepository;
import com.api.universidad.repository.MatriculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            EstudianteRepository estudianteRepository,
            CursoRepository cursoRepository
    ) {
        this.matriculaRepository = matriculaRepository;
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Lista todas las matrículas con paginación
     */
    public Page<MatriculaDTO> listarTodas(Pageable pageable) {
        return matriculaRepository.findAll(pageable).map(MatriculaDTO::new);
    }

    /**
     * Obtiene una matrícula por su ID
     */
    public MatriculaDTO obtenerPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));
        return new MatriculaDTO(matricula);
    }

    /**
     * Lista las matrículas de un estudiante específico
     */
    public List<MatriculaDTO> listarPorEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId).stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Lista las matrículas de un curso específico
     */
    public List<MatriculaDTO> listarPorCurso(Long cursoId) {
        return matriculaRepository.findByCursoId(cursoId).stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva matrícula
     */
    @Transactional
    public MatriculaDTO crear(MatriculaDTO matriculaDTO) {
        Estudiante estudiante = estudianteRepository.findById(matriculaDTO.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + matriculaDTO.getEstudianteId()));
        
        Curso curso = cursoRepository.findById(matriculaDTO.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + matriculaDTO.getCursoId()));

        // Validar cupos disponibles
        if (curso.getCuposDisponibles() <= 0) {
            throw new BusinessException("No hay cupos disponibles en el curso: " + curso.getNombre());
        }

        // Validar prerrequisitos
        if (curso.getPrerequisitos() != null && !curso.getPrerequisitos().isEmpty()) {
            for (Asignatura pre : curso.getPrerequisitos()) {
                boolean cumplido = matriculaRepository.findByEstudianteId(estudiante.getId()).stream()
                        .anyMatch(m -> m.getCurso().getNombre().contains(pre.getNombre()) && "APROBADO".equals(m.getEstado()));
                
                if (!cumplido) {
                    throw new BusinessException("El estudiante no cumple con el prerrequisito: " + pre.getNombre());
                }
            }
        }

        // Reducir cupos disponibles
        curso.setCuposDisponibles(curso.getCuposDisponibles() - 1);
        cursoRepository.save(curso);

        Matricula matricula = Matricula.builder()
                .fechaMatricula(LocalDate.now())
                .estado("ACTIVA")
                .asistencias(0)
                .notaFinal(0.0)
                .estudiante(estudiante)
                .curso(curso)
                .build();

        return new MatriculaDTO(matriculaRepository.save(matricula));
    }

    /**
     * Actualiza una matrícula existente
     */
    @Transactional
    public MatriculaDTO actualizar(Long id, MatriculaDTO matriculaDTO) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));

        if (matriculaDTO.getEstado() != null) {
            matricula.setEstado(matriculaDTO.getEstado());
        }

        return new MatriculaDTO(matriculaRepository.save(matricula));
    }

    /**
     * Registra la nota final de una matrícula
     */
    @Transactional
    public MatriculaDTO registrarNota(Long id, Double nota) {
        if (nota < 0 || nota > 100) {
            throw new BusinessException("La nota final debe estar entre 0 y 100");
        }

        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));
        
        matricula.setNotaFinal(nota);
        
        // Actualizar estado automáticamente si es necesario
        if (nota >= 60) {
            matricula.setEstado("APROBADO");
        } else {
            matricula.setEstado("REPROBADO");
        }

        return new MatriculaDTO(matriculaRepository.save(matricula));
    }

    /**
     * Registra el número de asistencias
     */
    @Transactional
    public MatriculaDTO registrarAsistencia(Long id, Integer asistencias) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));
        
        matricula.setAsistencias(asistencias);
        return new MatriculaDTO(matriculaRepository.save(matricula));
    }

    /**
     * Elimina una matrícula
     */
    @Transactional
    public void eliminar(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));
        matriculaRepository.delete(matricula);
    }
}
