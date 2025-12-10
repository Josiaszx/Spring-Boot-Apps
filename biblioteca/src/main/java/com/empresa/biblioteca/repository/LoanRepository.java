package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Book;
import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.LoanStatus;
import com.empresa.biblioteca.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // listar todos los prestamos segun estatus dado
    List<Loan> findAllByStatusIs(LoanStatus status);
    Page<Loan> findAllByStatusIs(LoanStatus status, Pageable pageable); // para permitir paginacion

    // listar todos los prestamos de un miembro
    List<Loan> findAllByMemberIs(Member member);

    // obtener libro mas prestado
    @Query(value = "SELECT b.* FROM books b JOIN loans l ON l.book_id = b.id GROUP BY b.id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Book findMostBorrowedBook();
}
