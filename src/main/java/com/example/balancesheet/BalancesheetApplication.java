package com.example.balancesheet;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.repo.*;
import com.example.balancesheet.service.AssetAccountService;
import com.example.balancesheet.service.ClaimsAccountService;
import com.example.balancesheet.swingView.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@SpringBootApplication
public class BalancesheetApplication implements CommandLineRunner {

//    @Autowired
//    private AppConfig appConfig;

    @Autowired
    private BalanceSheetRepo balanceSheetRepo;

    @Autowired
    private AssetAccountService assetAccountService;

    @Autowired
    private ClaimsAccountService claimsAccountService;

    @Autowired
    private AssetAccountRepo assetAccountRepo;

    @Autowired
    private ClaimsAccountRepo claimsAccountRepo;

    @Autowired
    private DebitTransactionRepo debitTransactionRepo;

    @Autowired
    private CreditTransactionRepo creditTransactionRepo;

    @Autowired
    private MainFrame mainFrame;

    public static void main(String[] args) {
        //SpringApplication.run(BalancesheetApplication.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(BalancesheetApplication.class);

        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        debitTransactionRepo.deleteAll();
        creditTransactionRepo.deleteAll();
        assetAccountRepo.deleteAll();
        claimsAccountRepo.deleteAll();
        balanceSheetRepo.deleteAll();
        BalanceSheet balanceSheet = new BalanceSheet("JUSRAD");
        balanceSheetRepo.save(balanceSheet);
        BalanceSheet bs = balanceSheetRepo.findByEntityName(balanceSheet.getEntityName());
        assetAccountService.addAssetAccounts(bs);
        claimsAccountService.addClaimsAccounts(bs);
        mainFrame.onCreate();
    }
}
