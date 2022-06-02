package com.example.balancesheet.swingView;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.BalanceSheet;
import com.example.balancesheet.model.ClaimsAccount;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.ClaimsAccountRepo;
import com.example.balancesheet.service.AssetAccountService;
import com.example.balancesheet.service.BalanceSheetService;
import com.itextpdf.text.DocumentException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

@Component
public class MainFrame extends JFrame{

    @Autowired
    AssetAccountService assetAccountService;

    @Autowired
    private AssetAccountRepo assetAccountRepo;

    @Autowired
    private ClaimsAccountRepo claimsAccountRepo;

    @Autowired
    private TransactionFrame transactionFrame;

    @Autowired
    private BalanceSheetService balanceSheetService;

    private JLabel claims;
    private JPanel assetsPanel;
    private JPanel claimsPanel;
    private JPanel mainPanel;
    private JLabel assets;
    private JButton generujBilansButton;


    public void onCreate(BalanceSheet balanceSheet){
        setContentPane(mainPanel);
        setTitle("Generator Bilansu");
        setSize(900,650);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createAssetList();
        createClaimsList();
        setVisible(true);
        transactionFrame.addListenerToSubmit();
        generujBilansButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                balanceSheetService.getClosingSums(balanceSheet);
                balanceSheetService.generateBalanceInPdf(balanceSheet);
                JOptionPane.showMessageDialog(MainFrame.this, "Bilans został wygenerowany! ;)", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                System.exit(1);
            }
        });
    }

    public void createAssetList() {
        List<AssetAccount> assetAccounts = assetAccountRepo.findAll();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        int y = 0;

        JButton[] buttonAssets = new JButton[11];
        int index=0;

        for(AssetAccount a : assetAccounts){
            gbc.gridx = 0;
            gbc.gridy = y;
            JLabel label = new JLabel(a.getName());
            assetsPanel.add(label,gbc);

            gbc.gridx = 1;
            gbc.gridy = y;
            buttonAssets[index] = new JButton("Zaksieguj");

            buttonAssets[index].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    transactionFrame.onCreate(a);
                }
            });

            assetsPanel.add(buttonAssets[index],gbc);

            y++;
            index++;
        }

    }

    public void createClaimsList() {
        List<ClaimsAccount> claimsAccounts = claimsAccountRepo.findAll();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        int y = 0;

        for(ClaimsAccount a : claimsAccounts){
            gbc.gridx = 0;
            gbc.gridy = y;
            JLabel label = new JLabel(a.getName());
            claimsPanel.add(label,gbc);

            gbc.gridx = 1;
            gbc.gridy = y;
            JButton button = new JButton("Zaksięguj");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    transactionFrame.onCreate(a);
                }
            });

            claimsPanel.add(button,gbc);

            y++;
        }

    }

}
