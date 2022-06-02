package com.example.balancesheet.service;

import com.example.balancesheet.config.AppConfig;
import com.example.balancesheet.model.*;
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

    public double getClosingSum(ClaimsAccount claimsAccount){
        double closingSum = 0.0;

        for(CreditTransaction creditTransaction : claimsAccount.getCreditTransactions()){
            closingSum += creditTransaction.getSum();
           // System.out.println(creditTransaction.getDescription() + " " + closingSum);
        }

        for (DebitTransaction debitTransaction : claimsAccount.getDebitTransactions()){
            closingSum -= debitTransaction.getSum();
            //System.out.println(debitTransaction.getDescription() + " " + closingSum);
        }
        claimsAccount.setClosingSum(closingSum);
        claimsAccountRepo.save(claimsAccount);
        //System.out.println(claimsAccount.getName() + " " + closingSum);
        return closingSum;
    }
}
