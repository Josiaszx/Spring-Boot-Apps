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
        Author author = authorRepository.findById(id).orElse(new Author());
        return new AuthorDTO(author);
    }

    // mostrar libros de una autor
    public List<BookDTO>  findBooksByAuthorId(Long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(IllegalArgumentException::new);
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
    public AuthorDTO modifyAuthor(Long authorId, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(IllegalArgumentException::new);
        author = updateAuthor(authorId, authorDTO);
        author = authorRepository.save(author);
        return new AuthorDTO(author);
    }

    // eliminar autor segun id
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        authorRepository.deleteById(author.getId());
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

    // metodo para actualizar un Author a partir de un AuthorDTO con posible valores null
    public Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(IllegalArgumentException::new);

        if (authorDTO.getName() != null) author.setName(authorDTO.getName());
        if (authorDTO.getLastName() != null) author.setLastName(authorDTO.getLastName());
        if (authorDTO.getBiography() != null) author.setBiography(authorDTO.getBiography());
        if (authorDTO.getEmail() != null) author.setEmail(authorDTO.getEmail());
        if (authorDTO.getNationality() != null) author.setNationality(authorDTO.getNationality());
        if (authorDTO.getBirthDate() != null) author.setBirthDate(authorDTO.getBirthDate());

        return author;
    }
}
