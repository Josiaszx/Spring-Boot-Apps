package com.api.universidad.controller;

import com.api.universidad.dto.EstudianteDTO;
import com.api.universidad.dto.PerfilAcademicoDTO;
import com.api.universidad.dto.PostEstudianteDTO;
import com.api.universidad.service.EstudianteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
    Endpoints a implementar:
        1 - GET 	/api/estudiantes 	Listar todos los estudiantes (paginado)
        2 - GET 	/api/estudiantes/{id} 	Obtener estudiante por ID con su perfil
        3 - GET 	/api/estudiantes/codigo/{codigo} 	Buscar estudiante por código
        4 - GET 	/api/estudiantes/{id}/matriculas 	Obtener matrículas de un estudiante
        5 - GET 	/api/estudiantes/{id}/perfil 	Obtener perfil académico del estudiante
        6 - POST 	/api/estudiantes 	Crear nuevo estudiante con perfil
        7 - PUT 	/api/estudiantes/{id} 	Actualizar estudiante
        8 - PATCH 	/api/estudiantes/{id}/perfil 	Actualizar perfil académico
        9 - DELETE 	/api/estudiantes/{id} 	Eliminar estudiante
*/

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // 1 - GET 	/api/estudiantes 	Listar todos los estudiantes (paginado)
    @GetMapping
    public List<EstudianteDTO> findAll(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int elementos
    ) {
        Pageable pageable = PageRequest.of(pagina, elementos);
        return estudianteService.findAll(pageable).getContent();
    }

    // 2 - GET 	/api/estudiantes/{id} 	Obtener estudiante por ID con su perfil
    @GetMapping("/{id}")
    public EstudianteDTO findById(@PathVariable Long id) {
        return estudianteService.findById(id);
    }

    // 3 - GET 	/api/estudiantes/codigo/{codigo} 	Buscar estudiante por codigo
    @GetMapping("/codigo/{codigo}")
    public EstudianteDTO findByCodigo(@PathVariable String codigo) {
        return estudianteService.findByCodigo(codigo);
    }

    // 4 - GET 	/api/estudiantes/{id}/matriculas 	Obtener matrículas de un estudiante
    // ...

    // 5 - GET 	/api/estudiantes/{id}/perfil 	Obtener perfil académico del estudiante
    @GetMapping("/{id}/perfil")
    public PerfilAcademicoDTO getPerfilAcademico(@PathVariable Long id) {
        return estudianteService.getPerfilAcademico(id);
    }

    // 6 - POST /api/estudiantes 	Crear nuevo estudiante con perfil
    @PostMapping
    public EstudianteDTO save(@RequestBody PostEstudianteDTO newEstudiante) {
        return estudianteService.save(newEstudiante);
    }

    // 7 - PATCH 	/api/estudiantes/{id} 	Actualizar estudiante
    @PatchMapping("/{id}")
    public EstudianteDTO save(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        return estudianteService.update(estudianteDTO, id);
    }

    // 8 - PATCH 	/api/estudiantes/{id}/perfil 	Actualizar perfil académico
    @PatchMapping("/{id}/perfil")
    public EstudianteDTO updatePerfilAcademico(
            @PathVariable Long id,
            @RequestBody PerfilAcademicoDTO perfilAcademicoDTO
    ) {
        return estudianteService.updatePerfilAcademico(perfilAcademicoDTO, id);
    }

    // 9 - DELETE 	/api/estudiantes/{id} 	Eliminar estudiante
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        estudianteService.delete(id);
    }
}
