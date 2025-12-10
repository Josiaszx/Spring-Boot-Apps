package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Endpoints a implementar
        1 - GET /api/authors ... Listar todos
        2 - GET /api/authors/{id} ... Obtener por ID
        3 - GET /api/authors/{id}/books ... Libros de un autor
        4 - POST /api/authors ... Crear autor
        5 - PUT /api/authors/{id} ... Actualizar
        6 - DELETE /api/authors/{id} ... Eliminar
*/

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    final private AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // 1 - GET /api/authors ... Listar todos
    @GetMapping
    public Page<AuthorDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        var pageable = PageRequest.of(page, size);
        return authorService.findAll(pageable);
    }

    // 2 - GET /api/authors/{id} ... Obtener por ID
    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    // 3 - GET /api/authors/{id}/books ... Libros de un autor
    @GetMapping("/{id}/books")
    public List<BookDTO> findBooksByAuthorId(@PathVariable Long id) {
        return authorService.findBooksByAuthorId(id);
    }

    // 4 - POST /api/authors ... Crear autor
    @PostMapping
    public AuthorDTO save(@RequestBody AuthorDTO authorDTO) {
        return authorService.save(authorDTO);
    }

    // 5 - PUT /api/authors/{id} ... Actualizar
    @PutMapping("/{id}")
    public AuthorDTO modifyAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        return  authorService.modifyAuthor(id, authorDTO);
    }

    // 6 - DELETE /api/authors/{id} ... Eliminar
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
