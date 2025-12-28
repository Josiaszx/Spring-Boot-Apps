package com.api.universidad.service;

import com.api.universidad.dto.AsignaturaDTO;
import com.api.universidad.dto.CursoDTO;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Asignatura;
import com.api.universidad.repository.AsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    /**
     * Obtiene todas las asignaturas registradas.
     * @return Lista de AsignaturaDTO
     */
    @Transactional(readOnly = true)
    public List<AsignaturaDTO> listarTodas() {
        return asignaturaRepository.findAll().stream()
                .map(AsignaturaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca una asignatura por su ID.
     * @param id ID de la asignatura
     * @return AsignaturaDTO encontrada
     * @throws ResourceNotFoundException si no existe
     */
    @Transactional(readOnly = true)
    public AsignaturaDTO obtenerPorId(Long id) {
        Asignatura asignatura = buscarPorId(id);
        return new AsignaturaDTO(asignatura);
    }

    /**
     * Obtiene la lista de cursos que requieren una asignatura específica como prerrequisito.
     * @param id ID de la asignatura
     * @return Lista de CursoDTO
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursosPorAsignatura(Long id) {
        Asignatura asignatura = buscarPorId(id);
        return asignatura.getCursos().stream()
                .map(CursoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva asignatura.
     * @param dto Datos de la asignatura
     * @return Asignatura creada como DTO
     */
    @Transactional
    public AsignaturaDTO crear(AsignaturaDTO dto) {
        Asignatura asignatura = Asignatura.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .creditosRequeridos(dto.getCreditosRequeridos())
                .build();
        
        return new AsignaturaDTO(asignaturaRepository.save(asignatura));
    }

    /**
     * Actualiza los datos de una asignatura existente.
     * @param id ID de la asignatura a actualizar
     * @param dto Nuevos datos
     * @return Asignatura actualizada como DTO
     */
    @Transactional
    public AsignaturaDTO actualizar(Long id, AsignaturaDTO dto) {
        Asignatura asignatura = buscarPorId(id);
        
        asignatura.setNombre(dto.getNombre());
        asignatura.setDescripcion(dto.getDescripcion());
        asignatura.setCreditosRequeridos(dto.getCreditosRequeridos());
        
        return new AsignaturaDTO(asignaturaRepository.save(asignatura));
    }

    /**
     * Elimina una asignatura por su ID.
     * @param id ID de la asignatura
     */
    @Transactional
    public void eliminar(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asignatura con ID " + id + " no encontrada");
        }
        asignaturaRepository.deleteById(id);
    }

    /**
     * Método auxiliar para buscar la entidad Asignatura.
     */
    private Asignatura buscarPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura con ID " + id + " no encontrada"));
    }
}
