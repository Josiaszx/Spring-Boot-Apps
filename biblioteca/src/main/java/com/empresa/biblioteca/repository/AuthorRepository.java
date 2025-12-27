package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // chquear existencia de autor segun id
    boolean existsAuthorByIdIs(Long id);
}
