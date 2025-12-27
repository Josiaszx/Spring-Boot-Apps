package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.PostLoanDTO;
import com.empresa.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Endpoints a implementar
        1 - POST /api/loans ... Registrar préstamo
        2 - PUT /api/loans/{id}/return ... Devolver libro
        3 - GET /api/loans/active ... Préstamos activos
        4 - GET /api/loans/overdue ... Préstamos vencidos
        5 - GET /api/members/{id}/loans ... Historial de miembro
*/

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    final private LoanService loanService;
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // 1 - POST /api/loans ... Registrar préstamo
    @PostMapping
    public LoanDTO save(@Valid @RequestBody PostLoanDTO postLoanDTO) {
        return loanService.save(postLoanDTO);
    }

    // 2 - PUT /api/loans/{id}/return - devolver libro
    @PutMapping("/{id}/return")
    public LoanDTO returnBook(@PathVariable Long id){
        return loanService.returnBook(id);
    }

    // 3 - GET /api/loans/active - Préstamos activos
    @GetMapping("/active")
    public Page<LoanDTO> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return loanService.findAllActive(pageable);
    }

    // 4 - GET /api/loans/overdue - Prestamos vencidos
    @GetMapping("/overdue")
    public List<LoanDTO> findAllOverdue() {
        return loanService.findAllOverdue();
    }

    // 5 - GET /api/loans/{id} - Prestamo segun id
    @GetMapping("/{id}")
    public LoanDTO findById(@PathVariable long id) {
        return loanService.findById(id);
    }


}
