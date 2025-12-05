package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.AuthorDTO;
import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.dto.CategoryDTO;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.dto.PostBookDTO;
import com.empresa.biblioteca.model.Category;
import com.empresa.biblioteca.repository.AuthorRepository;
import com.empresa.biblioteca.repository.BookRepository;
import com.empresa.biblioteca.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookService(
            BookRepository bookRepository,
            CategoryRepository categoryRepository,
            AuthorRepository authorRepository
    ) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }


    // listar libros
    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();
        return toDTOList(books);
    }

    // mostrar libro segun id
    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return toDTO(book);
    }

    // agregar libro
    public BookDTO save(PostBookDTO postBookDTO) {
        var category = categoryRepository.findById(postBookDTO.getCategoryId())
                .orElseThrow(IllegalArgumentException::new);

        var author = authorRepository.findById(postBookDTO.getAuthorId())
                .orElseThrow(IllegalArgumentException::new);

        var book = toEntity(postBookDTO, author, category);
        book =  bookRepository.save(book);
        return toDTO(book);
    }

    // actualizar libro
    public BookDTO update(PostBookDTO postBookDTO, Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        var author = book.getAuthor();
        var category = book.getCategory();

        updateBook(book, postBookDTO);
        book = bookRepository.save(book);
        return toDTO(book);
    }

    // eliminar libro
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    // metodos de mappeo
    // Book a BookDTO
    public BookDTO toDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthorName(book.getAuthor().getName() +  " " + book.getAuthor().getLastName());
        bookDTO.setCategory(book.getCategory().getName());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setTotalCopies(book.getTotalCopies());
        bookDTO.setAvailableCopies(book.getAvailableCopies());
        bookDTO.setPublishedDate(book.getPublishedDate());
        return bookDTO;
    }

    // List<Book> a List<BookDTO>
    public List<BookDTO> toDTOList(List<Book> books) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book : books) {
            bookDTOList.add(toDTO(book));
        }
        return bookDTOList;
    }

    // PostBookDTO a Book
    public Book toEntity(PostBookDTO postBookDTO, Author author, Category category) {
        Book book = new Book();
        book.setIsbn(postBookDTO.getIsbn());
        book.setTitle(postBookDTO.getTitle());
        book.setPublishedDate(postBookDTO.getPublishedDate());
        book.setPublisher(postBookDTO.getPublisher());
        book.setAvailableCopies(postBookDTO.getAvailableCopies());
        book.setTotalCopies(postBookDTO.getTotalCopies());
        book.setCategory(category);
        book.setAuthor(author);
        return book;
    }

    // actualizar libro
    public void updateBook(Book book, PostBookDTO postBookDTO) {

        if (postBookDTO.getTitle() != null) book.setTitle(postBookDTO.getTitle());
        if (postBookDTO.getPublishedDate() != null) book.setPublishedDate(postBookDTO.getPublishedDate());
        if (postBookDTO.getIsbn() != null) book.setIsbn(postBookDTO.getIsbn());
        if (postBookDTO.getAvailableCopies() != null) book.setAvailableCopies(postBookDTO.getAvailableCopies());
        if (postBookDTO.getTotalCopies() != null) book.setTotalCopies(postBookDTO.getTotalCopies());
        if (postBookDTO.getPublisher() != null) book.setPublisher(postBookDTO.getPublisher());

        if (postBookDTO.getCategoryId() != null) {
            var category = categoryRepository.findById(postBookDTO.getCategoryId())
                    .orElseThrow(IllegalArgumentException::new);
            book.setCategory(category);
        }
        if  (postBookDTO.getAuthorId() != null) {
            var author = authorRepository.findById(postBookDTO.getAuthorId())
                    .orElseThrow(IllegalArgumentException::new);
            book.setAuthor(author);
        }
    }

}
