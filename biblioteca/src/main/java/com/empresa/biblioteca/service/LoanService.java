package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.LoanStatus;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.repository.BookRepository;
import com.empresa.biblioteca.repository.LoanRepository;
import com.empresa.biblioteca.repository.MemberRepository;
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
        Loan loan = toEntity(postLoanDTO);
        loan = loanRepository.save(loan);
        return toDTO(loan);
    }

    // devolver libro
    public LoanDTO returnBook(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(IllegalStateException::new);
        loan.setStatus(LoanStatus.CLOSED);
        loan.setReturnDate(LocalDate.now());
        loan = loanRepository.save(loan);
        return toDTO(loan);
    }

    // listar prestamos activos
    public List<LoanDTO> findAllActive() {
        return toDTOList(loanRepository.findAllByStatusIs(LoanStatus.ACTIVE));
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
        return toDTO(loan);
    }


    // ----- metodos de mappeo -----
    // Loan a LoanDTO
    public LoanDTO toDTO(Loan loan) {
        var loanDTO = new LoanDTO();
        loanDTO.setDueDate(loan.getDueDate());
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setStatus(loan.getStatus());
        loanDTO.setBookName(loan.getBook().getTitle());
        loanDTO.setMemberName(loan.getMember().getFirstName() + " " + loan.getMember().getLastName());
        loanDTO.setReturnDate(loan.getReturnDate());
        return loanDTO;
    }

    // List<Loan> a List<LoanDTO>
    public List<LoanDTO> toDTOList(List<Loan> loans) {
        List<LoanDTO> loansDTO = new ArrayList<>();
        for (Loan loan : loans) {
            loansDTO.add(toDTO(loan));
        }
        return loansDTO;
    }

    // PostLoanDTO a Loan
    public Loan toEntity(PostLoanDTO postLoanDTO) {
        var loan = new Loan();
        var book = bookRepository.findById(postLoanDTO.getBookId())
                        .orElseThrow(IllegalArgumentException::new);
        var member = memberRepository.findById(postLoanDTO.getMemberId())
                        .orElseThrow(IllegalArgumentException::new);

        loan.setDueDate(postLoanDTO.getDueDate());
        loan.setLoanDate(postLoanDTO.getLoanDate());
        loan.setStatus(postLoanDTO.getStatus());
        loan.setBook(book);
        loan.setMember(member);
        loan.setReturnDate(postLoanDTO.getReturnDate());
        return loan;
    }
}
