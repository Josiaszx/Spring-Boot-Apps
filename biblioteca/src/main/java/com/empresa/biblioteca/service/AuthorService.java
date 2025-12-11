package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.repository.AuthorRepository;
import com.empresa.biblioteca.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {

    final private AuthorRepository authorRepository;
    final private BookRepository bookRepository;
    final private BookService bookService;

    public AuthorService(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            BookService bookservice
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookservice;
    }

    // mostrar todos los autores
    public Page<AuthorDTO> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(AuthorDTO::new);
    }

    // mostrar autor por id
    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->  new NoSuchElementException("Author Not Found"));

        return new AuthorDTO(author);
    }

    // mostrar libros de un autor
    public List<BookDTO>  findBooksByAuthorId(Long authorId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() ->  new NoSuchElementException("Author Not Found"));

        var books = bookRepository.findAllByAuthorIs(author);
        return bookService.toDTOList(books);
    }

    // agregar nuevo autor
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = new Author(authorDTO);
        author = authorRepository.save(author);
        return new AuthorDTO(author);
    }

    // actualizar actor
    public AuthorDTO modifyAuthor(Long authorId, Author UpdatedAuthor) {
        Author author = updateAuthor(authorId, UpdatedAuthor);
        author = authorRepository.save(author);
        return new AuthorDTO(author);
    }

    // eliminar autor segun id
    public void deleteById(Long id) {
        boolean existsAuthor = authorRepository.existsAuthorByIdIs(id);
        if (!existsAuthor) throw new NoSuchElementException("Author Not Found");
        authorRepository.deleteById(id);
    }


    // ---- Metodos de mappeo ----
    // convertir Author en AuthorDTO
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
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() ->  new NoSuchElementException("Author Not Found"));

        if (updatedAuthor.getName() != null) author.setName(updatedAuthor.getName());
        if (updatedAuthor.getLastName() != null) author.setLastName(updatedAuthor.getLastName());
        if (updatedAuthor.getBiography() != null) author.setBiography(updatedAuthor.getBiography());
        if (updatedAuthor.getEmail() != null) author.setEmail(updatedAuthor.getEmail());
        if (updatedAuthor.getNationality() != null) author.setNationality(updatedAuthor.getNationality());
        if (updatedAuthor.getBirthDate() != null) author.setBirthDate(updatedAuthor.getBirthDate());

        return author;
    }
}
