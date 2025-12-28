package com.api.universidad.service;

import com.api.universidad.dto.EstudianteDTO;
import com.api.universidad.dto.PerfilAcademicoDTO;
import com.api.universidad.dto.PostEstudianteDTO;
import com.api.universidad.exception.ResourceNotFoundException;
import com.api.universidad.model.Estudiante;
import com.api.universidad.model.PerfilAcademico;
import com.api.universidad.repository.EstudianteRepository;
import com.api.universidad.repository.PerfilAcademicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRespository;
    private final PerfilAcademicoRepository perfilAcademicoRepository;

    public EstudianteService(EstudianteRepository estudianteRespository,
                             PerfilAcademicoRepository perfilAcademicoRepository
    ) {
        this.estudianteRespository = estudianteRespository;
        this.perfilAcademicoRepository = perfilAcademicoRepository;
    }

    // listar todos los estudiantes
    public Page<EstudianteDTO> findAll(Pageable pageable) {
        var page = estudianteRespository.findAll(pageable);
        return page.map(EstudianteDTO::new);
    }

    // obtener estudiantes por id
    public EstudianteDTO findById(Long id) {
        var estudiante = estudianteRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        return new EstudianteDTO(estudiante);
    }

    // obtener estudiante por codigo
    public EstudianteDTO findByCodigo(String codigo) {
        var estudiante = estudianteRespository.findByCodigo((codigo))
                .orElseThrow(() -> new ResourceNotFoundException());

        return new EstudianteDTO(estudiante);
    }

    // obtener perfil academico del estudiante
    public PerfilAcademicoDTO getPerfilAcademico(Long id) {
        var estudiante = estudianteRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        var perfilAcademico = estudiante.getPerfilAcademico();
        return new PerfilAcademicoDTO(perfilAcademico);
    }

    // guardar estudiante con perfil academico
    public EstudianteDTO save(PostEstudianteDTO newEstudiante) {
        var perfilAcademico = new PerfilAcademico(newEstudiante);
        perfilAcademicoRepository.save(perfilAcademico);

        var estudiante = new Estudiante(newEstudiante);
        estudiante.setPerfilAcademico(perfilAcademico);
        estudiante = estudianteRespository.save(estudiante);

        return new EstudianteDTO(estudiante);
    }

    // actualizar estudiante
    public EstudianteDTO update(EstudianteDTO estudianteDTO, Long id) {
        var estudiante = estudianteRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        if (estudianteDTO.getCodigo() != null) estudiante.setCodigo(estudianteDTO.getCodigo());
        if(estudianteDTO.getNombre() != null) estudiante.setNombre(estudianteDTO.getNombre());
        if(estudianteDTO.getApellido() != null) estudiante.setApellido(estudianteDTO.getApellido());

        estudiante = estudianteRespository.save(estudiante);
        return new EstudianteDTO(estudiante);
    }

    // actualizar perfil academico
    public EstudianteDTO updatePerfilAcademico(PerfilAcademicoDTO perfilDTO, Long id) {
        var estudiante = estudianteRespository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        var perfil = estudiante.getPerfilAcademico();

        if (perfilDTO.getPromedioGeneral() != null) perfil.setPromedioGeneral(perfilDTO.getPromedioGeneral());
        if (perfilDTO.getCreditosCompletados() != null) perfil.setCreditosCompletados(perfilDTO.getCreditosCompletados());
        if (perfilDTO.getNivelAcademico() != null) perfil.setNivelAcademico(perfilDTO.getNivelAcademico());
        if (perfilDTO.getEstadoAcademico() != null) perfil.setEstadoAcademico(perfilDTO.getEstadoAcademico());
        perfil.setFechaActualizacion(LocalDate.now());

        perfil = perfilAcademicoRepository.save(perfil);
        estudiante = estudianteRespository.save(estudiante);
        return new EstudianteDTO(estudiante);
    }

    // eliminar estudiante
    public void delete(Long id) {
        estudianteRespository.deleteById(id);
    }
}
