package com.api.universidad.service;

import com.api.universidad.dto.CursoDTO;
import com.api.universidad.dto.ProfesorDTO;
import com.api.universidad.exception.BusinessException;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Departamento;
import com.api.universidad.model.Profesor;
import com.api.universidad.repository.CursoRepository;
import com.api.universidad.repository.DepartamentoRepository;
import com.api.universidad.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final DepartamentoRepository departamentoRepository;
    private final CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public Page<ProfesorDTO> listarTodos(Pageable pageable) {
        return profesorRepository.findAll(pageable).map(ProfesorDTO::new);
    }

    @Transactional(readOnly = true)
    public ProfesorDTO obtenerPorId(Long id) {
        return profesorRepository.findById(id)
                .map(ProfesorDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<ProfesorDTO> listarPorDepartamento(Long deptoId) {
        if (!departamentoRepository.existsById(deptoId)) {
            throw new ResourceNotFoundException("Departamento no encontrado con ID: " + deptoId);
        }
        return profesorRepository.findByDepartamentoId(deptoId).stream()
                .map(ProfesorDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProfesorDTO crear(ProfesorDTO profesorDTO) {
        if (profesorRepository.existsByCodigo(profesorDTO.getCodigo())) {
            throw new BusinessException("Ya existe un profesor con el código: " + profesorDTO.getCodigo());
        }
        if (profesorRepository.existsByEmail(profesorDTO.getEmail())) {
            throw new BusinessException("Ya existe un profesor con el email: " + profesorDTO.getEmail());
        }

        var departamento = departamentoRepository.findByNombre(profesorDTO.getNombreDepartamento())
                .orElse(null);

        var profesor = new Profesor(profesorDTO, departamento);

        return new ProfesorDTO(profesorRepository.save(profesor));
    }

    @Transactional
    public ProfesorDTO actualizar(Long id, ProfesorDTO profesorDTO) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + id));

        if (profesorDTO.getCodigo() != null && !profesor.getCodigo().equals(profesorDTO.getCodigo())) {
            if (profesorRepository.existsByCodigo(profesorDTO.getCodigo())) {
                throw new BusinessException("Ya existe un profesor con el código: " + profesorDTO.getCodigo());
            }
            profesor.setCodigo(profesorDTO.getCodigo());
        }

        if (profesorDTO.getEmail() != null && !profesor.getEmail().equals(profesorDTO.getEmail())) {
            if (profesorRepository.existsByEmail(profesorDTO.getEmail())) {
                throw new BusinessException("Ya existe un profesor con el email: " + profesorDTO.getEmail());
            }
            profesor.setEmail(profesorDTO.getEmail());
        }

        if (profesorDTO.getNombre() != null) profesor.setNombre(profesorDTO.getNombre());
        if (profesorDTO.getApellido() != null) profesor.setApellido(profesorDTO.getApellido());
        if (profesorDTO.getEspecialidad() != null) profesor.setEspecialidad(profesorDTO.getEspecialidad());
        if (profesorDTO.getFechaContratacion() != null) profesor.setFechaContratacion(profesorDTO.getFechaContratacion());

        return new ProfesorDTO(profesorRepository.save(profesor));
    }

    @Transactional
    public ProfesorDTO asignarDepartamento(Long id, Long deptoId) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + id));

        Departamento departamento = departamentoRepository.findById(deptoId)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + deptoId));

        profesor.setDepartamento(departamento);
        return new ProfesorDTO(profesorRepository.save(profesor));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profesor no encontrado con ID: " + id);
        }
        profesorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursos(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profesor no encontrado con ID: " + id);
        }
        return cursoRepository.findByProfesorId(id).stream()
                .map(CursoDTO::new)
                .collect(Collectors.toList());
    }
}
