package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    final private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public LoanDTO save(@Valid @RequestBody PostLoanDTO postLoanDTO) {
        return loanService.save(postLoanDTO);
    }

    @PutMapping("/{id}/return")
    public LoanDTO returnBook(@PathVariable Long id){
        return loanService.returnBook(id);
    }

    @GetMapping("/active")
    public Page<LoanDTO> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return loanService.findAllActive(pageable);
    }

    @GetMapping("/overdue")
    public List<LoanDTO> findAllOverdue() {
        return loanService.findAllOverdue();
    }

    @GetMapping("/{id}")
    public Loan findById(@PathVariable long id) {
        return loanService.findById(id);
    }


}
