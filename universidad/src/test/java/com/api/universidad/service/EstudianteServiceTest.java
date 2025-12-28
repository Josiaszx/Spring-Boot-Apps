package com.api.universidad.service;

import com.api.universidad.dto.EstudianteDTO;
import com.api.universidad.dto.PerfilAcademicoDTO;
import com.api.universidad.dto.PostEstudianteDTO;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.EstadoAcademico;
import com.api.universidad.model.Estudiante;
import com.api.universidad.model.NivelAcademico;
import com.api.universidad.model.PerfilAcademico;
import com.api.universidad.repository.EstudianteRepository;
import com.api.universidad.repository.PerfilAcademicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRespository;

    @Mock
    private PerfilAcademicoRepository perfilAcademicoRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    private Estudiante estudiante;
    private PerfilAcademico perfilAcademico;

    @BeforeEach
    void setUp() {
        perfilAcademico = PerfilAcademico.builder()
                .id(1L)
                .promedioGeneral(8.5)
                .creditosCompletados(100)
                .nivelAcademico(NivelAcademico.TERCERO)
                .estadoAcademico(EstadoAcademico.ACTIVO)
                .fechaActualizacion(LocalDate.now())
                .build();

        estudiante = Estudiante.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .codigo("EST001")
                .email("juan.perez@universidad.com")
                .fechaIngreso(LocalDate.now())
                .carrera("Ingeniería")
                .perfilAcademico(perfilAcademico)
                .build();
    }

    @Test
    void findAll() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Estudiante> estudiantes = List.of(estudiante);
        Page<Estudiante> page = new PageImpl<>(estudiantes, pageable, 1);

        when(estudianteRespository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        Page<EstudianteDTO> result = estudianteService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(estudiante.getNombre(), result.getContent().get(0).getNombre());
        assertEquals(estudiante.getCodigo(), result.getContent().get(0).getCodigo());

        verify(estudianteRespository, times(1)).findAll(pageable);
    }

    @Test
    void findById_Success() {
        // Arrange
        when(estudianteRespository.findById(1L)).thenReturn(Optional.of(estudiante));

        // Act
        EstudianteDTO result = estudianteService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(estudiante.getNombre(), result.getNombre());
        verify(estudianteRespository, times(1)).findById(1L);
    }

    @Test
    void findById_NotFound() {
        // Arrange
        when(estudianteRespository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> estudianteService.findById(1L));
        verify(estudianteRespository, times(1)).findById(1L);
    }

    @Test
    void findByCodigo_Success() {
        // Arrange
        when(estudianteRespository.findByCodigo("EST001")).thenReturn(Optional.of(estudiante));

        // Act
        EstudianteDTO result = estudianteService.findByCodigo("EST001");

        // Assert
        assertNotNull(result);
        assertEquals(estudiante.getCodigo(), result.getCodigo());
        verify(estudianteRespository, times(1)).findByCodigo("EST001");
    }

    @Test
    void findByCodigo_NotFound() {
        // Arrange
        when(estudianteRespository.findByCodigo("EST001")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> estudianteService.findByCodigo("EST001"));
        verify(estudianteRespository, times(1)).findByCodigo("EST001");
    }

    @Test
    void getPerfilAcademico_Success() {
        // Arrange
        when(estudianteRespository.findById(1L)).thenReturn(Optional.of(estudiante));

        // Act
        PerfilAcademicoDTO result = estudianteService.getPerfilAcademico(1L);

        // Assert
        assertNotNull(result);
        assertEquals(perfilAcademico.getPromedioGeneral(), result.getPromedioGeneral());
        verify(estudianteRespository, times(1)).findById(1L);
    }

    @Test
    void save_Success() {
        // Arrange
        PostEstudianteDTO postDTO = PostEstudianteDTO.builder()
                .nombre("Juan")
                .apellido("Perez")
                .codigo("EST001")
                .email("juan.perez@universidad.com")
                .carrera("Ingeniería")
                .promedioGeneral(8.5)
                .creditosCompletados(100)
                .nivelAcademico("TERCERO")
                .estadoAcademico("ACTIVO")
                .fechaActualizacion(LocalDate.now())
                .build();

        when(perfilAcademicoRepository.save(any(PerfilAcademico.class))).thenReturn(perfilAcademico);
        when(estudianteRespository.save(any(Estudiante.class))).thenReturn(estudiante);

        // Act
        EstudianteDTO result = estudianteService.save(postDTO);

        // Assert
        assertNotNull(result);
        assertEquals(postDTO.getNombre(), result.getNombre());
        verify(perfilAcademicoRepository, times(1)).save(any(PerfilAcademico.class));
        verify(estudianteRespository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void update_Success() {
        // Arrange
        EstudianteDTO updateDTO = EstudianteDTO.builder()
                .nombre("Juan Updated")
                .apellido("Perez Updated")
                .codigo("EST001")
                .build();

        when(estudianteRespository.findById(1L)).thenReturn(Optional.of(estudiante));
        when(estudianteRespository.save(any(Estudiante.class))).thenReturn(estudiante);

        // Act
        EstudianteDTO result = estudianteService.update(updateDTO, 1L);

        // Assert
        assertNotNull(result);
        verify(estudianteRespository, times(1)).findById(1L);
        verify(estudianteRespository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void updatePerfilAcademico_Success() {
        // Arrange
        PerfilAcademicoDTO perfilDTO = PerfilAcademicoDTO.builder()
                .promedioGeneral(9.0)
                .creditosCompletados(120)
                .build();

        when(estudianteRespository.findById(1L)).thenReturn(Optional.of(estudiante));
        when(perfilAcademicoRepository.save(any(PerfilAcademico.class))).thenReturn(perfilAcademico);
        when(estudianteRespository.save(any(Estudiante.class))).thenReturn(estudiante);

        // Act
        EstudianteDTO result = estudianteService.updatePerfilAcademico(perfilDTO, 1L);

        // Assert
        assertNotNull(result);
        verify(estudianteRespository, times(1)).findById(1L);
        verify(perfilAcademicoRepository, times(1)).save(any(PerfilAcademico.class));
        verify(estudianteRespository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void delete_Success() {
        // Act
        estudianteService.delete(1L);

        // Assert
        verify(estudianteRespository, times(1)).deleteById(1L);
    }

}