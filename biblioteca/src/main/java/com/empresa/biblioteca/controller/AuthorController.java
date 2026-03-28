package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.service.AuthorService;
import com.empresa.biblioteca.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    final private AuthorService authorService;
    final private BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public Page<Author> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        var pageable = PageRequest.of(page, size);
        return authorService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/{id}/books")
    public List<Book> findBooksByAuthorId(@PathVariable Long id) {
        var author = authorService.findById(id);
        return bookService.findAllByAuthor(author);
    }

    @PostMapping
    public AuthorDTO save(@Valid @RequestBody AuthorDTO authorDTO) {
        return authorService.save(authorDTO);
    }

    @PutMapping("/{id}")
    public AuthorDTO modifyAuthor(@PathVariable Long id, @RequestBody Author author) {
        return  authorService.modifyAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
