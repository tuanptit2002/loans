package com.loans.loans.repository;

import com.loans.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findLoansByMobileNumber (String numberMobile);
    Optional<Loans> findByLoanNumber(String loanNumber);
}
