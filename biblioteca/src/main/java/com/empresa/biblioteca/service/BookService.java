package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.BookDTO;
import com.empresa.biblioteca.exception.InvalidOperationException;
import com.empresa.biblioteca.model.Author;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.dto.PostBookDTO;
import com.empresa.biblioteca.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookService(
            BookRepository bookRepository,
            CategoryService categoryService,
            AuthorService authorService
    ) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    public Book findByTitle(String title) {
        if (title.equals("n/a")) throw new NoSuchElementException("Title is required"); 
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    public List<BookDTO> findByAvailableCopies() {
        List<Book> books = bookRepository.findAllByAvailableCopies();
        if (books == null) return new ArrayList<>();
        return toDTOList(books);
    }

    public BookDTO save(PostBookDTO postBookDTO) {
        var availableCopies = postBookDTO.getAvailableCopies();
        var totalCopies = postBookDTO.getTotalCopies();
        if (totalCopies < availableCopies) throw new InvalidOperationException("Total copies cannot be less than the available copies");
        var category = categoryService.findById(postBookDTO.getCategoryId());
        var author = authorService.findById(postBookDTO.getAuthorId());
        var book = new Book(postBookDTO,  author, category);
        book =  bookRepository.save(book);
        return new BookDTO(book);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public BookDTO update(PostBookDTO postBookDTO, Long id) {
        var book = findById(id);
        updateBook(book, postBookDTO);
        book = bookRepository.save(book);
        return new BookDTO(book);
    }

    public void delete(Long id) {
        boolean existsElement = bookRepository.existsById(id);
        if (!existsElement)  throw new NoSuchElementException("Book not found");
        bookRepository.deleteById(id);
    }

    public List<Book> findAllByAuthor(Author author) {
        return bookRepository.findAllByAuthorIs(author);
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
            var category = categoryService.findById(postBookDTO.getCategoryId());
            book.setCategory(category);
        }

        if  (postBookDTO.getAuthorId() != null) {
            var author = authorService.findById(postBookDTO.getAuthorId());
            book.setAuthor(author);
        }
    }

}
