package com.empresa.biblioteca.service;

import com.empresa.biblioteca.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    final private BookRepository bookRepository;
    public LoanService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // listar prestamos
//    public List<>
}
