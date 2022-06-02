package com.example.balancesheet.repo;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.ClaimsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimsAccountRepo extends JpaRepository<ClaimsAccount,Long> {
    ClaimsAccount findByName(String name);
}
