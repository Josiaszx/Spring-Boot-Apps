package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.repository.AuthorRepository;
import com.empresa.biblioteca.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    final private AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
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

    //
}
