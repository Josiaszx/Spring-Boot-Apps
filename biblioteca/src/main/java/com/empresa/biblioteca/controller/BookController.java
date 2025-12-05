package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.dto.PostBookDTO;
import com.empresa.biblioteca.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    final private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDTO save(@RequestBody PostBookDTO postBookDTO) {
        return bookService.save(postBookDTO);
    }

    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody PostBookDTO postBookDTO) {
        return bookService.update(postBookDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

}
