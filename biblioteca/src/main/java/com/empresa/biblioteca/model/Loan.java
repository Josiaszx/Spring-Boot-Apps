package com.empresa.biblioteca.model;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // se mappeara como string en la tabla de la BD
    private LoanStatus status;

    // constructores y metodos de acceso

    public Loan(Long id, Book book, Member member, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Loan(PostLoanDTO postLoanDTO, Member member, Book book) {
        this.loanDate = postLoanDTO.getLoanDate();
        this.dueDate = postLoanDTO.getDueDate();
        this.returnDate = postLoanDTO.getReturnDate();
        this.status = postLoanDTO.getStatus();
        this.member = member;
        this.book = book;
    }

    public Loan() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
