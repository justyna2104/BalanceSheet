package com.example.balancesheet.swingView;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.ClaimsAccount;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.ClaimsAccountRepo;
import com.example.balancesheet.service.AssetAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JLabel claims;
    private JPanel assetsPanel;
    private JPanel claimsPanel;
    private JPanel mainPanel;
    private JLabel assets;

    public void onCreate() throws Exception {
        setContentPane(mainPanel);
        setTitle("Generator Bilansu");
        setSize(900,650);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createAssetList();
        createClaimsList();
        setVisible(true);
        transactionFrame.addListenerToSubmit();
        //transactionFrame.onCreate(assetAccountRepo.findById(1L));
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
            //gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;
            gbc.gridy = y;
            JLabel label = new JLabel(a.getName());
            assetsPanel.add(label,gbc);

            //gbc.anchor = GridBagConstraints.WEST;
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
            //gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;
            gbc.gridy = y;
            JLabel label = new JLabel(a.getName());
            claimsPanel.add(label,gbc);

            //gbc.anchor = GridBagConstraints.WEST;
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
