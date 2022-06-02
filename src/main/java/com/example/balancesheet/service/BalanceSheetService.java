package com.example.balancesheet.service;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.model.ClaimsAccount;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.BalanceSheetRepo;
import com.example.balancesheet.repo.ClaimsAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceSheetService {

    @Autowired
    AssetAccountService assetAccountService;

    @Autowired
    AssetAccountRepo assetAccountRepo;

    @Autowired
    ClaimsAccountService claimsAccountService;

    @Autowired
    ClaimsAccountRepo claimsAccountRepo;

    @Autowired
    BalanceSheetRepo balanceSheetRepo;

    public void getClosingSums(BalanceSheet balanceSheet){
        double assetClosingSum = 0.0;
        double claimsClosingSum = 0.0;

        for (AssetAccount a : balanceSheet.getAssets()){
            AssetAccount assetAccount = assetAccountRepo.findByName(a.getName());
            assetClosingSum += assetAccountService.getClosingSum(assetAccount);
        }

        for(ClaimsAccount c : balanceSheet.getClaims()){
            ClaimsAccount claimsAccount = claimsAccountRepo.findByName(c.getName());
            claimsClosingSum += claimsAccountService.getClosingSum(claimsAccount);
        }

        balanceSheet.setAssetSum(assetClosingSum);
        balanceSheet.setClaimsSum(claimsClosingSum);
        balanceSheetRepo.save(balanceSheet);
    }
}
