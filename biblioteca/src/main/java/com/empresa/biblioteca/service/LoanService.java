package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
import com.empresa.biblioteca.exception.InvalidOperationException;
import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.LoanStatus;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.repository.BookRepository;
import com.empresa.biblioteca.repository.LoanRepository;
import com.empresa.biblioteca.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanService {

    final private LoanRepository loanRepository;
    final private BookService bookService;
    final private MemberService memberService;

    public LoanService(
            BookService bookService,
            LoanRepository loanRepository,
            MemberService memberService
    ) {
        this.bookService = bookService;
        this.loanRepository = loanRepository;
        this.memberService = memberService;
    }

    public LoanDTO save(PostLoanDTO postLoanDTO) {
        var book = bookService.findById(postLoanDTO.getBookId());
        if (book.getAvailableCopies() == 0) throw new InvalidOperationException("Book not available");
        var member = memberService.findById(postLoanDTO.getMemberId());
        var loan = new Loan(postLoanDTO, member, book);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.save(book);
        loan = loanRepository.save(loan);
        return new LoanDTO(loan);
    }

    public LoanDTO returnBook(Long loanId) {
        var loan = findById(loanId);
        if (loan.getStatus() == LoanStatus.CLOSED) throw new InvalidOperationException("Loan already closed");
        var book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.save(book);
        loan.setStatus(LoanStatus.CLOSED);
        loan.setReturnDate(LocalDate.now());
        loan = loanRepository.save(loan);
        return new LoanDTO(loan);
    }

    public Page<LoanDTO> findAllActive(Pageable pageable) {
        var loans = loanRepository.findAllByStatusIs(LoanStatus.ACTIVE, pageable);
        return loans.map(LoanDTO::new);
    }

    public List<LoanDTO> findAllOverdue() {
        return toDTOList(loanRepository.findAllByStatusIs(LoanStatus.OVERDUE));
    }

    public Loan findById(long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }

    public List<LoanDTO> findAllLoansFromOneUser(Long userId) {
        var member = memberService.findById(userId);
        return toDTOList(loanRepository.findAllByMemberIs(member));
    }

    public Book findMostBorrowedBook() {
        return loanRepository.findMostBorrowedBook();
    }

    public List<LoanDTO> findAllMemberLoans(Long idUser) {
        var member = memberService.findById(idUser);
        var memberLoans = loanRepository.findAllByMemberIs(member);
        return toDTOList(memberLoans);
    }
    // ----- metodos de mappeo -----

    // List<Loan> a List<LoanDTO>
    public List<LoanDTO> toDTOList(List<Loan> loans) {
        List<LoanDTO> loansDTO = new ArrayList<>();
        for (Loan loan : loans) {
            loansDTO.add(new LoanDTO(loan));
        }
        return loansDTO;
    }
}
