package com.empresa.biblioteca.dto;

import com.empresa.biblioteca.model.LoanStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PostLoanDTO {

    @NotNull(message = "book id cannot be null")
    private Long bookId;

    @NotNull(message = "member id cannot be null")
    private Long memberId;

    @NotNull(message = "loan date cannot be empty")
    private LocalDate loanDate;

    @NotNull(message = "due date cannot be empty")
    private LocalDate dueDate;

    @NotNull(message = "return date cannot be empty")
    private LocalDate returnDate;

    private LoanStatus status;

    public PostLoanDTO(Long bookId, Long memberId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate == null ? LocalDate.now() : loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status == null ? LoanStatus.ACTIVE : status;
    }

    public PostLoanDTO() {}

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
