package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // obtener libro por isbn (International Standard Book Number)
    public Book findByIsbn(String isbn);

    // obtener libro por su titulo
    public Book findByTitle(String title);

    // obtener todos los libros disponibles
    @Query("select b from Book b where b.availableCopies > 0")
    public List<Book> findAllByAvailableCopies();

    // obtener libros de un autor
    List<Book> findAllByAuthorIs(Author author);
}
