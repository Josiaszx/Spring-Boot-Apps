package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.repository.AuthorRepository;
import com.empresa.biblioteca.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    final private AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // agregar autor
    @PostMapping
    public AuthorDTO save(@RequestBody AuthorDTO authorDTO) {
        return authorService.save(authorDTO);
    }

    // listar autores
    @GetMapping
    public List<AuthorDTO> findAll() {
        return authorService.findAll();
    }

    // listar autor
    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

}
