package com.example.balancesheet.service;

import com.example.balancesheet.config.AppConfig;
import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.model.ClaimsAccount;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.BalanceSheetRepo;
import com.example.balancesheet.repo.ClaimsAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimsAccountService {
    @Autowired
    private ClaimsAccountRepo claimsAccountRepo;

    @Autowired
    private BalanceSheetRepo balanceSheetRepo;

    @Autowired
    private AppConfig appConfig;

    public void addClaimsAccounts(BalanceSheet balanceSheet){
        appConfig.getAccountNames().getClaims().forEach(claims -> {
            ClaimsAccount claimsAccount = new ClaimsAccount(claims, balanceSheet);
            claimsAccountRepo.save(claimsAccount);
            balanceSheet.getClaims().add(claimsAccount);
            balanceSheetRepo.save(balanceSheet);
        });
    }
}
