package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.LoanStatus;
import com.empresa.biblioteca.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // listar prestamos activos
    List<Loan> findAllByDueDateIsAfter(LocalDate dueDateBefore);

    // listar todos los prestamos segun estatus dado
    List<Loan> findAllByStatusIs(LoanStatus status);

    // listar todos los prestamos de un miembro
    List<Loan> findAllByMemberIs(Member member);
}
