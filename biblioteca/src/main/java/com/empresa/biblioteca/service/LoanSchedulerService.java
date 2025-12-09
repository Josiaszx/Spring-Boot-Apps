package com.empresa.biblioteca.service;

import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.LoanStatus;
import com.empresa.biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

// clase utilizada para verificar cada 24 horas la fecha de vencimiento de los prestmos
// y cambiar su estado a vencida si es necesrio
@Service
public class LoanSchedulerService {

    @Autowired
    private LoanRepository loanRepository;

    // Se ejecuta cada 24 horas (a las 00:00)
    @Scheduled(cron = "0 0 0 * * *") // indica que se ejecutara cada dia a las 00:00
    public void checkOverdueLoans() {
        LocalDate today = LocalDate.now();

        List<Loan> activeLoans = loanRepository.findAllByStatusIs(LoanStatus.ACTIVE);

        for (Loan loan : activeLoans) {
            if (loan.getDueDate().isBefore(today)) {
                loan.setStatus(LoanStatus.OVERDUE);
                loanRepository.save(loan);
            }
        }
    }
}
