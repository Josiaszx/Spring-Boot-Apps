package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
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
                        .orElseThrow(IllegalArgumentException::new);
        var member = memberRepository.findById(postLoanDTO.getMemberId())
                        .orElseThrow(IllegalArgumentException::new);

        Loan loan = new Loan(postLoanDTO, member, book);

        if (book.getAvailableCopies() == 0) return null;
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        bookRepository.save(book);
        loan = loanRepository.save(loan);
        return new LoanDTO(loan);
    }

    // devolver libro
    public LoanDTO returnBook(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(IllegalStateException::new);
        if (loan.getStatus() == LoanStatus.CLOSED) return null;

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
//        return toDTOList(loanRepository.findAllByStatusIs(pageable, LoanStatus.ACTIVE));
        return loans.map(LoanDTO::new);
    }

    // listar todos los prestamos vencidos
    public List<LoanDTO> findAllOverdue() {
        return toDTOList(loanRepository.findAllByStatusIs(LoanStatus.OVERDUE));
    }

    // listar todos los prestamos de un miembro
    public List<LoanDTO> findAllLoansFromOneUser(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(IllegalStateException::new);
        return toDTOList(loanRepository.findAllByMemberIs(member));
    }

    // mostrar prestamo segun id
    public LoanDTO findById(long id) {
        var loan = loanRepository.findById(id).orElseThrow(IllegalStateException::new);
        return new LoanDTO(loan);
    }


    // ----- metodos de mappeo -----
    // Loan a LoanDTO
//    public LoanDTO toDTO(Loan loan) {
//        var loanDTO = new LoanDTO();
//        loanDTO.setDueDate(loan.getDueDate());
//        loanDTO.setLoanDate(loan.getLoanDate());
//        loanDTO.setStatus(loan.getStatus());
//        loanDTO.setBookName(loan.getBook().getTitle());
//        loanDTO.setMemberName(loan.getMember().getFirstName() + " " + loan.getMember().getLastName());
//        loanDTO.setReturnDate(loan.getReturnDate());
//        return loanDTO;
//    }

    // List<Loan> a List<LoanDTO>
    public List<LoanDTO> toDTOList(List<Loan> loans) {
        List<LoanDTO> loansDTO = new ArrayList<>();
        for (Loan loan : loans) {
            loansDTO.add(new LoanDTO(loan));
        }
        return loansDTO;
    }

    // PostLoanDTO a Loan
//    public Loan toEntity(PostLoanDTO postLoanDTO) {
//        var loan = new Loan();
//        var book = bookRepository.findById(postLoanDTO.getBookId())
//                        .orElseThrow(IllegalArgumentException::new);
//        var member = memberRepository.findById(postLoanDTO.getMemberId())
//                        .orElseThrow(IllegalArgumentException::new);
//
//        loan.setDueDate(postLoanDTO.getDueDate());
//        loan.setLoanDate(postLoanDTO.getLoanDate());
//        loan.setStatus(postLoanDTO.getStatus());
//        loan.setBook(book);
//        loan.setMember(member);
//        loan.setReturnDate(postLoanDTO.getReturnDate());
//        return loan;
//    }
}
