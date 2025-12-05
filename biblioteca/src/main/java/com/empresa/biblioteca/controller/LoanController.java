package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    final private BookService bookService;
    public LoanController(BookService bookService) {
        this.bookService = bookService;
    }


}
