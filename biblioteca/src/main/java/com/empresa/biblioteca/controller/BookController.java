package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.dto.PostBookDTO;
import com.empresa.biblioteca.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/*
Endpoints a implementar:
    1 - GET /api/books - Obtener lista paginada de libros
    2 - GET /api/books/{id} - Obtener libro por ID
    3 - GET /api/books/isbn/{isbn} - Obtener libro por ISBN
    4 - GET /api/books/search?title=xyz - Buscar libros por título
    5 - GET /api/books/available - Obtener libros disponibles
    6 - POST /api/books - Crear nuevo libro
    7 - PUT /api/books/{id} - Actualizar libro
    8 - DELETE /api/books/{id} - Eliminar libro
    9 - GET /api/books/stats - obtener datos sobre los prestaos de libros
*/



@RestController
@RequestMapping("/api/books")
public class BookController {

    final private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 1 - GET /api/books - Obtener lista paginada de libros
    @GetMapping
    public Page<BookDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        var pageable = PageRequest.of(page, size);
        return bookService.findAll(pageable);
    }

    // 2 - GET /api/books/{id} - Obtener libro por ID
    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // 3 - GET /api/books/isbn/{isbn} - Obtener libro por ISBN
    @GetMapping("/isbn/{isbn}")
    public BookDTO findByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    // 4 - GET /api/books/search?title=xyz - Buscar libros por título
    @GetMapping("/search")
    public BookDTO findByTitle(@RequestParam String title) {
        return bookService.findByTitle(title);
    }

    // 5 - GET /api/books/available - Obtener libros disponibles
    @GetMapping("/available")
    public List<BookDTO> findAllAvailableCopies() {
        return bookService.findByAvailableCopies();
    }

    // 6 - PUT /api/books/{id} - Actualizar libro
    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody PostBookDTO postBookDTO) {
        return bookService.update(postBookDTO, id);
    }

    // 7 - POST /api/books - Crear nuevo libro
    @PostMapping
    public BookDTO save(@Valid @RequestBody PostBookDTO postBookDTO) {
        return bookService.save(postBookDTO);
    }

    // 8 - DELETE /api/books/{id} - Eliminar libro
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    // 9 - GET /api/books/stats - obtener datos sobre los prestaos de libros
    @GetMapping("/stats")
    public Map<String, String> getStats() {
        return bookService.findMostBorrowedBooks();
    }
}
