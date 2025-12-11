package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
import com.empresa.biblioteca.exception.InvalidOperationException;
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

    final private BookRepository bookRepository;
    final private LoanRepository loanRepository;
    final private MemberRepository memberRepository;
    public LoanService(
            BookRepository bookRepository,
            LoanRepository loanRepository,
            MemberRepository memberRepository
    ) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
    }

    // registrar prestamo
    public LoanDTO save(PostLoanDTO postLoanDTO) {

        var book = bookRepository.findById(postLoanDTO.getBookId())
                        .orElseThrow(() -> new NoSuchElementException("Book not found"));

        var member = memberRepository.findById(postLoanDTO.getMemberId())
                        .orElseThrow(() -> new NoSuchElementException("Member not found"));

        Loan loan = new Loan(postLoanDTO, member, book);

        if (book.getAvailableCopies() == 0) throw new InvalidOperationException("Book not available");
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        bookRepository.save(book);
        loan = loanRepository.save(loan);
        return new LoanDTO(loan);
    }

    // devolver libro
    public LoanDTO returnBook(Long loanId) {
        var loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));

        if (loan.getStatus() == LoanStatus.CLOSED) throw new InvalidOperationException("Loan already closed");

        var book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        loan.setStatus(LoanStatus.CLOSED);
        loan.setReturnDate(LocalDate.now());
        loan = loanRepository.save(loan);
        return new LoanDTO(loan);
    }

    // listar prestamos activos
    public Page<LoanDTO> findAllActive(Pageable pageable) {
        var loans = loanRepository.findAllByStatusIs(LoanStatus.ACTIVE, pageable);
        return loans.map(LoanDTO::new);
    }

    // listar todos los prestamos vencidos
    public List<LoanDTO> findAllOverdue() {
        return toDTOList(loanRepository.findAllByStatusIs(LoanStatus.OVERDUE));
    }

    // mostrar prestamo segun id
    public LoanDTO findById(long id) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));

        return new LoanDTO(loan);
    }

    // listar todos los prestamos de un miembro
    public List<LoanDTO> findAllLoansFromOneUser(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Member not found"));

        return toDTOList(loanRepository.findAllByMemberIs(member));
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
