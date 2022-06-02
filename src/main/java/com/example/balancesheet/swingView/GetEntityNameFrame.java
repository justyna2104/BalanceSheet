package com.example.balancesheet.swingView;

import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.repo.*;
import com.example.balancesheet.service.AssetAccountService;
import com.example.balancesheet.service.ClaimsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class GetEntityNameFrame extends JFrame{
    private JTextField entityName;
    private JButton startBtn;
    private JPanel mainPanel;

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


    public void onCreate(){
        setContentPane(mainPanel);
        setTitle("Generator Bilansu");
        setSize(500,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                debitTransactionRepo.deleteAll();
                creditTransactionRepo.deleteAll();
                assetAccountRepo.deleteAll();
                claimsAccountRepo.deleteAll();
                balanceSheetRepo.deleteAll();
                BalanceSheet balanceSheet = new BalanceSheet(entityName.getText());
                balanceSheetRepo.save(balanceSheet);
                BalanceSheet bs = balanceSheetRepo.findByEntityName(balanceSheet.getEntityName());
                assetAccountService.addAssetAccounts(bs);
                claimsAccountService.addClaimsAccounts(bs);
                mainFrame.onCreate(bs);
                setVisible(false);
            }
        });


    }
}
