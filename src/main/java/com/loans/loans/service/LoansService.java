package com.loans.loans.service;

import com.loans.loans.dto.LoansDto;

public interface LoansService {

    void createLoan(String numberMobile);

    LoansDto fetchLoan(String numberMobile);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String numberMobile);
}
