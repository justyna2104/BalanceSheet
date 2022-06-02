package com.example.balancesheet.repo;

import com.example.balancesheet.model.CreditTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditTransactionRepo extends JpaRepository<CreditTransaction,Long> {
}
