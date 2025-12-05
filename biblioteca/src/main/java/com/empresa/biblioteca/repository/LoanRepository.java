package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
