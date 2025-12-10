package com.empresa.biblioteca.dto;

import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.LoanStatus;

import java.time.LocalDate;

public class LoanDTO {

    private String bookName;

    private String memberName;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private LoanStatus status;

    public LoanDTO(String bookName, String memberName, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) {
        this.bookName = bookName;
        this.memberName = memberName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public LoanDTO(Loan loan) {
        this.bookName = loan.getBook().getTitle();
        this.memberName = loan.getMember().getFirstName() + " " + loan.getMember().getLastName();
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.returnDate = loan.getReturnDate();
        this.status = loan.getStatus();
    }


    public LoanDTO() {}


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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
