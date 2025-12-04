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
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    // mostrar todos los autores
    public List<AuthorDTO>  findAll() {
        return toDTOList(authorRepository.findAll());
    }

    // mostrar autor por id
    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(Math.toIntExact(id)).orElse(new Author());
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



}
