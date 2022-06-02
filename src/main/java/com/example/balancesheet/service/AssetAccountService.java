package com.example.balancesheet.service;

import com.example.balancesheet.config.AppConfig;
import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.model.CreditTransaction;
import com.example.balancesheet.model.DebitTransaction;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.BalanceSheetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetAccountService {

    @Autowired
    private AssetAccountRepo assetAccountRepo;

    @Autowired
    private BalanceSheetRepo balanceSheetRepo;

    @Autowired
    private AppConfig appConfig;

    public void addAssetAccounts(BalanceSheet balanceSheet){
        appConfig.getAccountNames().getAssets().forEach(asset -> {
            AssetAccount assetAccount = new AssetAccount(asset, balanceSheet);
            assetAccountRepo.save(assetAccount);
            balanceSheet.getAssets().add(assetAccount);
            balanceSheetRepo.save(balanceSheet);
        });
    }

    public double getClosingSum(AssetAccount assetAccount){
        double closingSum = 0.0;

        for (DebitTransaction debitTransaction : assetAccount.getDebitTransactions()){
            closingSum += debitTransaction.getSum();
        }

        for(CreditTransaction creditTransaction : assetAccount.getCreditTransactions()){
            closingSum -= creditTransaction.getSum();
        }
        assetAccount.setClosingSum(closingSum);
        assetAccountRepo.save(assetAccount);
        return closingSum;
    }
}
