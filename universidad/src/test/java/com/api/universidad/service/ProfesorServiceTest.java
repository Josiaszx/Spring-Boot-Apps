package com.api.universidad.service;

import com.api.universidad.dto.ProfesorDTO;
import com.api.universidad.exception.BusinessException;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Departamento;
import com.api.universidad.model.Profesor;
import com.api.universidad.repository.DepartamentoRepository;
import com.api.universidad.repository.ProfesorRepository;
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
class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private ProfesorService profesorService;

    private Profesor profesor;
    private Departamento departamento;
    private ProfesorDTO profesorDTO;

    @BeforeEach
    void setUp() {
        departamento = Departamento.builder()
                .id(1L)
                .nombre("Matemáticas")
                .codigo("MAT")
                .build();

        profesor = Profesor.builder()
                .id(1L)
                .nombre("Carlos")
                .apellido("Gomez")
                .codigo("PROF001")
                .email("carlos.gomez@universidad.com")
                .especialidad("Cálculo")
                .fechaContratacion(LocalDate.now())
                .departamento(departamento)
                .build();

        profesorDTO = new ProfesorDTO(profesor);
    }

    @Test
    void listarTodos() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Profesor> page = new PageImpl<>(List.of(profesor), pageable, 1);
        when(profesorRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<ProfesorDTO> result = profesorService.listarTodos(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(profesor.getNombre(), result.getContent().get(0).getNombre());
        verify(profesorRepository, times(1)).findAll(pageable);
    }

    @Test
    void obtenerPorId_Success() {
        // Arrange
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        // Act
        ProfesorDTO result = profesorService.obtenerPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(profesor.getNombre(), result.getNombre());
        verify(profesorRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_NotFound() {
        // Arrange
        when(profesorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profesorService.obtenerPorId(1L));
        verify(profesorRepository, times(1)).findById(1L);
    }

    @Test
    void listarPorDepartamento_Success() {
        // Arrange
        when(departamentoRepository.existsById(1L)).thenReturn(true);
        when(profesorRepository.findByDepartamentoId(1L)).thenReturn(List.of(profesor));

        // Act
        List<ProfesorDTO> result = profesorService.listarPorDepartamento(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profesor.getNombre(), result.get(0).getNombre());
        verify(departamentoRepository, times(1)).existsById(1L);
        verify(profesorRepository, times(1)).findByDepartamentoId(1L);
    }

    @Test
    void listarPorDepartamento_NotFound() {
        // Arrange
        when(departamentoRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profesorService.listarPorDepartamento(1L));
        verify(departamentoRepository, times(1)).existsById(1L);
        verify(profesorRepository, never()).findByDepartamentoId(any());
    }

    @Test
    void crear_Success() {
        // Arrange
        when(profesorRepository.existsByCodigo(profesorDTO.getCodigo())).thenReturn(false);
        when(profesorRepository.existsByEmail(profesorDTO.getEmail())).thenReturn(false);
        when(departamentoRepository.findByNombre(profesorDTO.getNombreDepartamento())).thenReturn(Optional.of(departamento));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesor);

        // Act
        ProfesorDTO result = profesorService.crear(profesorDTO);

        // Assert
        assertNotNull(result);
        assertEquals(profesorDTO.getCodigo(), result.getCodigo());
        verify(profesorRepository, times(1)).save(any(Profesor.class));
    }

    @Test
    void crear_CodigoDuplicado() {
        // Arrange
        when(profesorRepository.existsByCodigo(profesorDTO.getCodigo())).thenReturn(true);

        // Act & Assert
        assertThrows(BusinessException.class, () -> profesorService.crear(profesorDTO));
        verify(profesorRepository, never()).save(any());
    }

    @Test
    void crear_EmailDuplicado() {
        // Arrange
        when(profesorRepository.existsByCodigo(profesorDTO.getCodigo())).thenReturn(false);
        when(profesorRepository.existsByEmail(profesorDTO.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(BusinessException.class, () -> profesorService.crear(profesorDTO));
        verify(profesorRepository, never()).save(any());
    }

    @Test
    void actualizar_Success() {
        // Arrange
        ProfesorDTO dtoUpdate = ProfesorDTO.builder()
                .nombre("Carlos Alberto")
                .codigo("PROF001")
                .email("carlos.gomez@universidad.com")
                .build();

        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesor);

        // Act
        ProfesorDTO result = profesorService.actualizar(1L, dtoUpdate);

        // Assert
        assertNotNull(result);
        verify(profesorRepository, times(1)).save(any(Profesor.class));
        assertEquals("Carlos Alberto", profesor.getNombre());
    }

    @Test
    void actualizar_NotFound() {
        // Arrange
        when(profesorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profesorService.actualizar(1L, profesorDTO));
    }

    @Test
    void actualizar_CodigoDuplicado() {
        // Arrange
        ProfesorDTO dtoUpdate = ProfesorDTO.builder().codigo("NUEVO_COD").build();
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(profesorRepository.existsByCodigo("NUEVO_COD")).thenReturn(true);

        // Act & Assert
        assertThrows(BusinessException.class, () -> profesorService.actualizar(1L, dtoUpdate));
    }

    @Test
    void asignarDepartamento_Success() {
        // Arrange
        Departamento nuevoDepto = Departamento.builder().id(2L).nombre("Física").build();
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(departamentoRepository.findById(2L)).thenReturn(Optional.of(nuevoDepto));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesor);

        // Act
        ProfesorDTO result = profesorService.asignarDepartamento(1L, 2L);

        // Assert
        assertNotNull(result);
        assertEquals("Física", result.getNombreDepartamento());
        verify(profesorRepository, times(1)).save(profesor);
    }

    @Test
    void asignarDepartamento_ProfesorNotFound() {
        // Arrange
        when(profesorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profesorService.asignarDepartamento(1L, 2L));
    }

    @Test
    void asignarDepartamento_DeptoNotFound() {
        // Arrange
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(departamentoRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profesorService.asignarDepartamento(1L, 2L));
    }

    @Test
    void eliminar_Success() {
        // Arrange
        when(profesorRepository.existsById(1L)).thenReturn(true);

        // Act
        profesorService.eliminar(1L);

        // Assert
        verify(profesorRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminar_NotFound() {
        // Arrange
        when(profesorRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profesorService.eliminar(1L));
        verify(profesorRepository, never()).deleteById(any());
    }
}
