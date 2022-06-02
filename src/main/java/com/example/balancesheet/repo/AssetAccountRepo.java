package com.example.balancesheet.repo;

import com.example.balancesheet.model.AssetAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetAccountRepo extends JpaRepository<AssetAccount,Long> {
    AssetAccount findByName(String name);
    //AssetAccount findById(long id);
}
