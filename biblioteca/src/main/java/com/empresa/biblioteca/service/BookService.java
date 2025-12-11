package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.dto.PostBookDTO;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.repository.AuthorRepository;
import com.empresa.biblioteca.repository.BookRepository;
import com.empresa.biblioteca.repository.CategoryRepository;
import com.empresa.biblioteca.repository.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if  (book == null) throw new NoSuchElementException("Book not found");
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
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        var author = authorRepository.findById(postBookDTO.getAuthorId())
                .orElseThrow(() -> new NoSuchElementException("Author not found"));

        var book = new Book(postBookDTO,  author, category);
        book =  bookRepository.save(book);
        return new BookDTO(book);
    }

    // actualizar libro
    public BookDTO update(PostBookDTO postBookDTO, Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        updateBook(book, postBookDTO);
        book = bookRepository.save(book);
        return new BookDTO(book);
    }

    // eliminar libro
    public void delete(Long id) {
        boolean existsElement = bookRepository.existsById(id);
        if (!existsElement)  throw new NoSuchElementException("Book not found");

        bookRepository.deleteById(id);
    }

    // mostrar datos sobre los libros
    public Map<String, String> findMostBorrowedBooks() {

        Map<String, String> stats = new HashMap<>();

        // libro mas prestado
        var mostBorrowedBooks = loanRepository.findMostBorrowedBook();
        if (mostBorrowedBooks == null) mostBorrowedBooks = new Book();

        stats.put("Libro mas prestado", mostBorrowedBooks.getTitle());

        // miembro con mas prestamos activos
        var memberWithMostLoans = loanRepository.findMemberWithMostLoans();
        if (memberWithMostLoans == null) memberWithMostLoans = new Member();

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
                    .orElseThrow(() -> new NoSuchElementException("Category not found"));

            book.setCategory(category);
        }

        if  (postBookDTO.getAuthorId() != null) {
            var author = authorRepository.findById(postBookDTO.getAuthorId())
                    .orElseThrow(()  -> new NoSuchElementException("Author not found"));

            book.setAuthor(author);
        }
    }

}
