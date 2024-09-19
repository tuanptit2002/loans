package com.loans.loans.service.Impl;

import com.loans.loans.constants.LoansConstants;
import com.loans.loans.dto.LoansDto;
import com.loans.loans.entity.Loans;
import com.loans.loans.exception.LoanAlreadyExistsException;
import com.loans.loans.exception.ResourceNotFoundException;
import com.loans.loans.mapper.LoansMapper;
import com.loans.loans.repository.LoansRepository;
import com.loans.loans.service.LoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements LoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String numberMobile) {
        Optional<Loans> loans = loansRepository.findLoansByMobileNumber(numberMobile);
        if (loans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + numberMobile);
        }
        loansRepository.save(createNewLoan(numberMobile));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String numberMobile) {
        Loans loans = loansRepository.findLoansByMobileNumber(numberMobile).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", numberMobile)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber())
        );
        LoansMapper.mapToLoans(loansDto, new Loans());
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String numberMobile) {
        Loans loans = loansRepository.findLoansByMobileNumber(numberMobile).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", numberMobile)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
