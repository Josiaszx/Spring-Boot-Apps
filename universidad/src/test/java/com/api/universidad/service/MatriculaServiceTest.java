package com.api.universidad.service;

import com.api.universidad.dto.MatriculaDTO;
import com.api.universidad.exception.BusinessException;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Curso;
import com.api.universidad.model.Estudiante;
import com.api.universidad.model.Matricula;
import com.api.universidad.repository.CursoRepository;
import com.api.universidad.repository.EstudianteRepository;
import com.api.universidad.repository.MatriculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private MatriculaService matriculaService;

    private Estudiante estudiante;
    private Curso curso;
    private Matricula matricula;
    private MatriculaDTO matriculaDTO;

    @BeforeEach
    void setUp() {
        estudiante = Estudiante.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .build();

        curso = Curso.builder()
                .id(1L)
                .nombre("Matematicas")
                .cuposDisponibles(10)
                .prerequisitos(new ArrayList<>())
                .build();

        matricula = Matricula.builder()
                .id(1L)
                .estudiante(estudiante)
                .curso(curso)
                .estado("ACTIVA")
                .build();

        matriculaDTO = new MatriculaDTO();
        matriculaDTO.setEstudianteId(1L);
        matriculaDTO.setCursoId(1L);
    }

    @Test
    void testCrearMatriculaExitoso() {
        when(estudianteRepository.findById(1L)).thenReturn(Optional.of(estudiante));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        MatriculaDTO resultado = matriculaService.crear(matriculaDTO);

        assertNotNull(resultado);
        verify(cursoRepository, times(1)).save(curso);
        assertEquals(9, curso.getCuposDisponibles());
    }

    @Test
    void testCrearMatriculaSinCupos() {
        curso.setCuposDisponibles(0);
        when(estudianteRepository.findById(1L)).thenReturn(Optional.of(estudiante));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        assertThrows(BusinessException.class, () -> matriculaService.crear(matriculaDTO));
    }

    @Test
    void testRegistrarNotaExitoso() {
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        MatriculaDTO resultado = matriculaService.registrarNota(1L, 85.0);

        assertNotNull(resultado);
        assertEquals(85.0, matricula.getNotaFinal());
        assertEquals("APROBADO", matricula.getEstado());
    }

    @Test
    void testRegistrarNotaInvalida() {
        assertThrows(BusinessException.class, () -> matriculaService.registrarNota(1L, 105.0));
    }
}