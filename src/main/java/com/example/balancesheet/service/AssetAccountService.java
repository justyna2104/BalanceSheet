package com.example.balancesheet.service;

import com.example.balancesheet.config.AppConfig;
import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
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

    public AssetAccount findById(long id) throws Exception {
        AssetAccount assetAccount = assetAccountRepo.findById(id).orElseThrow(() -> new Exception());
        return assetAccount;
    }

}
