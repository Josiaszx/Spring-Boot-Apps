package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.dto.PostBookDTO;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.service.BookService;
import com.empresa.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    final private BookService bookService;
    final private LoanService loanService;

    public BookController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @GetMapping
    public Page<Book> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        var pageable = PageRequest.of(page, size);
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/isbn/{isbn}")
    public Book findByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @GetMapping("/title")
    public Book findByTitle(@RequestParam(defaultValue = "n/a") String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping("/available")
    public List<BookDTO> findAllAvailableCopies() {
        return bookService.findByAvailableCopies();
    }

    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody PostBookDTO postBookDTO) {
        return bookService.update(postBookDTO, id);
    }

    @PostMapping
    public BookDTO save(@Valid @RequestBody PostBookDTO postBookDTO) {
        return bookService.save(postBookDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/stats/borrows")
    public Book getStats() {
        return loanService.findMostBorrowedBook();
    }
}
