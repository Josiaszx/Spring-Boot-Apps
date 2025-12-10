package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.CategoryDTO;
import com.empresa.biblioteca.model.Category;
import com.empresa.biblioteca.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/*
    Endpoints implementados
        1 - GET /api/categories ... listar en forma de paginas
        2 - POST /api/categories ... agregar categoria
        3 - GET /api/categories ... obtener por id
        4 - PUT /api/categories ... actualizar
        5 - DELETE /api/categories ... eliminar
*/
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    final private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 1 - GET /api/categories ... listar en forma de paginas
    @GetMapping
    public Page<CategoryDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        var pageable = PageRequest.of(page, size);
        return categoryService.findAll(pageable);
    }

    // 2 - POST /api/categories ... agregar categoria
    @PostMapping
    public Category save(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.save(categoryDTO);
    }

    // 3 - GET /api/categories ... obtener por id
    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    // 4 - PUT /api/categories ... actualizar
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.update(categoryDTO, id);
    }

    // 5 - DELETE /api/categories ... eliminar
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }


}
