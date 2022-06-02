package com.example.balancesheet.repo;

import com.example.balancesheet.model.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepo extends JpaRepository<BalanceSheet,Long> {
    BalanceSheet findByEntityName(String name);
}
