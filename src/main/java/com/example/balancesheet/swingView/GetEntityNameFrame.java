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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class GetEntityNameFrame extends JFrame{
    private JTextField entityName;
    private JButton startBtn;
    private JPanel mainPanel;
    private JTextField dateTF;

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
        setSize(600,250);
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
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                if (!entityName.getText().isBlank()) {
                    if(isValidDate(dateTF.getText())){
                        BalanceSheet balanceSheet = new BalanceSheet(entityName.getText(), LocalDate.parse(dateTF.getText(), df));
                        balanceSheetRepo.save(balanceSheet);
                        BalanceSheet bs = balanceSheetRepo.findByEntityName(balanceSheet.getEntityName());
                        assetAccountService.addAssetAccounts(bs);
                        claimsAccountService.addClaimsAccounts(bs);
                        mainFrame.onCreate(bs);
                        setVisible(false);
                    }else {
                        JOptionPane.showMessageDialog(GetEntityNameFrame.this, "Błędny format daty!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(GetEntityNameFrame.this, "Nazwa nie może być pusta!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
