package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.repository.AuthorRepository;
import com.empresa.biblioteca.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {

    final private AuthorRepository authorRepository;

    public AuthorService(
            AuthorRepository authorRepository
    ) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() ->  new NoSuchElementException("Author Not Found"));
    }

    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = new Author(authorDTO);
        author = authorRepository.save(author);
        return new AuthorDTO(author);
    }

    public AuthorDTO modifyAuthor(Long authorId, Author UpdatedAuthor) {
        Author author = updateAuthor(authorId, UpdatedAuthor);
        author = authorRepository.save(author);
        return new AuthorDTO(author);
    }

    public void deleteById(Long id) {
        boolean existsAuthor = authorRepository.existsAuthorByIdIs(id);
        if (!existsAuthor) throw new NoSuchElementException("Author Not Found");
        authorRepository.deleteById(id);
    }

    // ---- Metodos de mappeo ----
    public List<AuthorDTO> toDTOList(List<Author> authors) {
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for (Author author : authors) {
            AuthorDTO authorDTO = new AuthorDTO(author);
            authorDTOs.add(authorDTO);
        }
        return authorDTOs;
    }

    // metodo para actualizar un Author con los campos no nulos de otro dado
    public Author updateAuthor(Long authorId, Author updatedAuthor) {
        Author author = findById(authorId);
        if (updatedAuthor.getName() != null) author.setName(updatedAuthor.getName());
        if (updatedAuthor.getLastName() != null) author.setLastName(updatedAuthor.getLastName());
        if (updatedAuthor.getBiography() != null) author.setBiography(updatedAuthor.getBiography());
        if (updatedAuthor.getEmail() != null) author.setEmail(updatedAuthor.getEmail());
        if (updatedAuthor.getNationality() != null) author.setNationality(updatedAuthor.getNationality());
        if (updatedAuthor.getBirthDate() != null) author.setBirthDate(updatedAuthor.getBirthDate());
        return author;
    }
}
