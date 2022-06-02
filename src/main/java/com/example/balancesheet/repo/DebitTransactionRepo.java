package com.example.balancesheet.repo;

import com.example.balancesheet.model.DebitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitTransactionRepo extends JpaRepository<DebitTransaction,Long> {
}
