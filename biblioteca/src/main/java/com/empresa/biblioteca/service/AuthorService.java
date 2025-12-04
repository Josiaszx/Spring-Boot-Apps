package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    final private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    // agregar nuevo autor
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = toEntity(authorDTO);
        author = authorRepository.save(author);
        return toDTO(author);
    }

    // mostrar todos los autores
    public List<AuthorDTO>  findAll() {
        return toDTOList(authorRepository.findAll());
    }

    // mostrar autor por id
    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(id).orElse(new Author());
        return toDTO(author);
    }

    // eliminar autor segun id
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        authorRepository.deleteById(author.getId());
    }

    // actualizar actor
    public AuthorDTO modifyAuthor(Long authorId, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(IllegalArgumentException::new);
        author = updateAuthor(authorId, authorDTO);
        author = authorRepository.save(author);
        return toDTO(author);
    }

    // metodos de mappeo
    // convertir lista de Author en lista de AuthorDTO
    public AuthorDTO toDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();

        authorDTO.setName(author.getName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setBiography(author.getBiography());
        authorDTO.setEmail(author.getEmail());
        authorDTO.setNationality(author.getNationality());
        authorDTO.setBirthDate(author.getBirthDate());

        return authorDTO;
    }

    // convertir Author en AuthorDTO
    public List<AuthorDTO> toDTOList(List<Author> authors) {
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for (Author author : authors) {
            AuthorDTO authorDTO = toDTO(author);
            authorDTOs.add(authorDTO);
        }
        return authorDTOs;
    }

    // convertir AuthorDTO a Author
    public Author toEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setLastName(authorDTO.getLastName());
        author.setBiography(authorDTO.getBiography());
        author.setEmail(authorDTO.getEmail());
        author.setNationality(authorDTO.getNationality());
        author.setBirthDate(authorDTO.getBirthDate());
        return author;
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
