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
import com.empresa.biblioteca.repository.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;

    public BookService(
            BookRepository bookRepository,
            CategoryRepository categoryRepository,
            AuthorRepository authorRepository,
            LoanRepository loanRepository
    ) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.loanRepository = loanRepository;
    }


    // listar libros
    public Page<BookDTO> findAll(Pageable pageable) {
        var books = bookRepository.findAll(pageable);
        return books.map(BookDTO::new);
    }

    // mostrar libro segun id
    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new BookDTO(book);
    }

    // mostrar libro segun isbn
    public BookDTO findByIsbn(String isbn) {
        var book = bookRepository.findByIsbn(isbn);
        if  (book == null) return new BookDTO();
        return new BookDTO(book);
    }

    // buscar libro por titulo
    public BookDTO findByTitle(String title) {
        var book = bookRepository.findByTitle(title);
        if  (book == null) return new BookDTO();
        return new BookDTO(book);
    }

    // listar libros disponibles
    public List<BookDTO> findByAvailableCopies() {
        List<Book> books = bookRepository.findAllByAvailableCopies();
        if (books == null) return new ArrayList<>();
        return toDTOList(books);
    }

    // agregar libro
    public BookDTO save(PostBookDTO postBookDTO) {
        var category = categoryRepository.findById(postBookDTO.getCategoryId())
                .orElseThrow(IllegalArgumentException::new);

        var author = authorRepository.findById(postBookDTO.getAuthorId())
                .orElseThrow(IllegalArgumentException::new);

        var book = new Book(postBookDTO,  author, category);
        book =  bookRepository.save(book);
        return new BookDTO(book);
    }

    // actualizar libro
    public BookDTO update(PostBookDTO postBookDTO, Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        var author = book.getAuthor();
        var category = book.getCategory();

        updateBook(book, postBookDTO);
        book = bookRepository.save(book);
        return new BookDTO(book);
    }

    // eliminar libro
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    // mostrar datos sobre los libros
    public Map<String, String> findMostBorrowedBooks() {

        Map<String, String> stats = new HashMap<>();

        // libro mas prestado
        var mostBorrowedBooks = loanRepository.findMostBorrowedBook();
        stats.put("Libro mas prestado", mostBorrowedBooks.getTitle());

        // miembro con mas prestamos activos
        var memberWithMostLoans = loanRepository.findMemberWithMostLoans();
        stats.put("Miembro con mas prestamos activos",
                        memberWithMostLoans.getFirstName() +
                        " " +
                        memberWithMostLoans.getLastName()
        );

        return stats;
    }

    // ----- metodos de mappeo -----

    // List<Book> a List<BookDTO>
    public List<BookDTO> toDTOList(List<Book> books) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book : books) {
            bookDTOList.add(new BookDTO(book));
        }
        return bookDTOList;
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
