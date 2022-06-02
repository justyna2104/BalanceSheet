package com.example.balancesheet.swingView;

import com.example.balancesheet.model.AssetAccount;
import com.example.balancesheet.model.ClaimsAccount;
import com.example.balancesheet.model.CreditTransaction;
import com.example.balancesheet.model.DebitTransaction;
import com.example.balancesheet.repo.AssetAccountRepo;
import com.example.balancesheet.repo.ClaimsAccountRepo;
import com.example.balancesheet.repo.CreditTransactionRepo;
import com.example.balancesheet.repo.DebitTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TransactionFrame extends JFrame{
    private JTextField descriptionTF;
    private JTextField sumTF;
    private JRadioButton reduceRB;
    private JComboBox listOfAccountsCB;
    private JRadioButton increaseRB;
    private JButton submitButton;
    private JPanel transactionLeftPanel;
    private JPanel transactionRightPanel;
    private JPanel mainPanel;
    private Object account;

    @Autowired
    private AssetAccountRepo assetAccountRepo;

    @Autowired
    private ClaimsAccountRepo claimsAccountRepo;

    @Autowired
    private CreditTransactionRepo creditTransactionRepo;

    @Autowired
    private DebitTransactionRepo debitTransactionRepo;


    public void onCreate(Object account){
        this.account = account;
        setContentPane(mainPanel);
        setTitle("Generator Bilansu");
        setSize(700,650);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        List<Object> comboBoxList = new ArrayList<>();
        List<AssetAccount> assetAccounts = assetAccountRepo.findAll();
        List<ClaimsAccount> claimsAccounts = claimsAccountRepo.findAll();

        descriptionTF.setText("");
        sumTF.setText("");
        reduceRB.setSelected(false);
        increaseRB.setSelected(false);

        comboBoxList.addAll(assetAccounts);
        comboBoxList.addAll(claimsAccounts);
        comboBoxList.forEach(object -> {
            listOfAccountsCB.addItem(object);
        });
        listOfAccountsCB.setEditable(false);

        setVisible(true);

    }

    public void addListenerToSubmit(){
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(descriptionTF.getText().isBlank() || sumTF.getText().isBlank() || !isDouble(sumTF.getText())){
                    JOptionPane.showMessageDialog(TransactionFrame.this, "Pola tekstowe muszą być wypełnione (i to poprawnie ;) )", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(Double.parseDouble(sumTF.getText()) < 0){
                    JOptionPane.showMessageDialog(TransactionFrame.this, "Suma nie może być ujemna", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if(reduceRB.isSelected() && increaseRB.isSelected()){
                        JOptionPane.showMessageDialog(TransactionFrame.this, "Wybierz jeden typ operacji", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if (!reduceRB.isSelected() && !increaseRB.isSelected()){
                        JOptionPane.showMessageDialog(TransactionFrame.this, "Wybierz jeden typ operacji", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(reduceRB.isSelected()){
                        if(account instanceof AssetAccount){
                            AssetAccount accountDB = assetAccountRepo.findByName(((AssetAccount) account).getName());
                            CreditTransaction creditTransaction = new CreditTransaction();
                            creditTransaction.setDescription(descriptionTF.getText());
                            creditTransaction.setSum(Double.parseDouble(sumTF.getText()));
                            creditTransaction.setPostDate(LocalDateTime.now());
                            creditTransaction.setAssetAccount(accountDB);
                            creditTransactionRepo.save(creditTransaction);
                            accountDB.getCreditTransactions().add(creditTransaction);
                            assetAccountRepo.save(accountDB);

                            if(listOfAccountsCB.getSelectedItem() instanceof AssetAccount){
                                DebitTransaction debitTransaction = new DebitTransaction();
                                debitTransaction.setDescription(descriptionTF.getText());
                                debitTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                debitTransaction.setPostDate(LocalDateTime.now());
                                assetAccountRepo.findById(((AssetAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(assetAccount -> {
                                    debitTransaction.setAssetAccount(assetAccount);
                                    debitTransactionRepo.save(debitTransaction);
                                    assetAccount.getDebitTransactions().add(debitTransaction);
                                    assetAccountRepo.save(assetAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                                return;
                            }else if(listOfAccountsCB.getSelectedItem() instanceof ClaimsAccount){
                                DebitTransaction debitTransaction = new DebitTransaction();
                                debitTransaction.setDescription(descriptionTF.getText());
                                debitTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                debitTransaction.setPostDate(LocalDateTime.now());
                                claimsAccountRepo.findById(((ClaimsAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(claimsAccount -> {
                                    debitTransaction.setClaimsAccount(claimsAccount);
                                    debitTransactionRepo.save(debitTransaction);
                                    claimsAccount.getDebitTransactions().add(debitTransaction);
                                    claimsAccountRepo.save(claimsAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }

                        }else if (account instanceof ClaimsAccount){
                            ClaimsAccount claimsAccountDB = claimsAccountRepo.findByName(((ClaimsAccount) account).getName());
                            DebitTransaction debitTransaction = new DebitTransaction();
                            debitTransaction.setDescription(descriptionTF.getText());
                            debitTransaction.setSum(Double.parseDouble(sumTF.getText()));
                            debitTransaction.setPostDate(LocalDateTime.now());
                            debitTransaction.setClaimsAccount(claimsAccountDB);
                            debitTransactionRepo.save(debitTransaction);
                            claimsAccountDB.getDebitTransactions().add(debitTransaction);
                            claimsAccountRepo.save(claimsAccountDB);

                            if(listOfAccountsCB.getSelectedItem() instanceof ClaimsAccount){
                                CreditTransaction creditTransaction = new CreditTransaction();
                                creditTransaction.setDescription(descriptionTF.getText());
                                creditTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                creditTransaction.setPostDate(LocalDateTime.now());
                                claimsAccountRepo.findById(((ClaimsAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(claimsAccount -> {
                                    creditTransaction.setClaimsAccount(claimsAccount);
                                    creditTransactionRepo.save(creditTransaction);
                                    claimsAccount.getCreditTransactions().add(creditTransaction);
                                    claimsAccountRepo.save(claimsAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }else if(listOfAccountsCB.getSelectedItem() instanceof AssetAccount){
                                CreditTransaction creditTransaction = new CreditTransaction();
                                creditTransaction.setDescription(descriptionTF.getText());
                                creditTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                creditTransaction.setPostDate(LocalDateTime.now());
                                assetAccountRepo.findById(((AssetAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(assetAccount -> {
                                    creditTransaction.setAssetAccount(assetAccount);
                                    creditTransactionRepo.save(creditTransaction);
                                    assetAccount.getCreditTransactions().add(creditTransaction);
                                    assetAccountRepo.save(assetAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }

                        }
                    }else if(increaseRB.isSelected()){ //TUTAJJJJJJ :)
                        if(account instanceof AssetAccount){
                            AssetAccount accountDB = assetAccountRepo.findByName(((AssetAccount) account).getName());
                            DebitTransaction debitTransaction = new DebitTransaction();
                            debitTransaction.setDescription(descriptionTF.getText());
                            debitTransaction.setSum(Double.parseDouble(sumTF.getText()));
                            debitTransaction.setPostDate(LocalDateTime.now());
                            debitTransaction.setAssetAccount(accountDB);
                            debitTransactionRepo.save(debitTransaction);
                            accountDB.getDebitTransactions().add(debitTransaction);
                            assetAccountRepo.save(accountDB);

                            if(listOfAccountsCB.getSelectedItem() instanceof AssetAccount){
                                CreditTransaction creditTransaction = new CreditTransaction();
                                creditTransaction.setDescription(descriptionTF.getText());
                                creditTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                creditTransaction.setPostDate(LocalDateTime.now());
                                assetAccountRepo.findById(((AssetAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(assetAccount -> {
                                    creditTransaction.setAssetAccount(assetAccount);
                                    creditTransactionRepo.save(creditTransaction);
                                    assetAccount.getCreditTransactions().add(creditTransaction);
                                    assetAccountRepo.save(assetAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }else if(listOfAccountsCB.getSelectedItem() instanceof ClaimsAccount){
                                CreditTransaction creditTransaction = new CreditTransaction();
                                creditTransaction.setDescription(descriptionTF.getText());
                                creditTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                creditTransaction.setPostDate(LocalDateTime.now());
                                claimsAccountRepo.findById(((ClaimsAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(claimsAccount -> {
                                    creditTransaction.setClaimsAccount(claimsAccount);
                                    creditTransactionRepo.save(creditTransaction);
                                    claimsAccount.getCreditTransactions().add(creditTransaction);
                                    claimsAccountRepo.save(claimsAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }

                        }else if(account instanceof ClaimsAccount){
                            ClaimsAccount claimsAccountDB = claimsAccountRepo.findByName(((ClaimsAccount) account).getName());
                            CreditTransaction creditTransaction = new CreditTransaction();
                            creditTransaction.setDescription(descriptionTF.getText());
                            creditTransaction.setSum(Double.parseDouble(sumTF.getText()));
                            creditTransaction.setPostDate(LocalDateTime.now());
                            creditTransaction.setClaimsAccount(claimsAccountDB);
                            creditTransactionRepo.save(creditTransaction);
                            claimsAccountDB.getCreditTransactions().add(creditTransaction);
                            claimsAccountRepo.save(claimsAccountDB);

                            if(listOfAccountsCB.getSelectedItem() instanceof AssetAccount){
                                DebitTransaction debitTransaction = new DebitTransaction();
                                debitTransaction.setDescription(descriptionTF.getText());
                                debitTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                debitTransaction.setPostDate(LocalDateTime.now());
                                assetAccountRepo.findById(((AssetAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(assetAccount -> {
                                    debitTransaction.setAssetAccount(assetAccount);
                                    debitTransactionRepo.save(debitTransaction);
                                    assetAccount.getDebitTransactions().add(debitTransaction);
                                    assetAccountRepo.save(assetAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }else if(listOfAccountsCB.getSelectedItem() instanceof ClaimsAccount){
                                DebitTransaction debitTransaction = new DebitTransaction();
                                debitTransaction.setDescription(descriptionTF.getText());
                                debitTransaction.setSum(Double.parseDouble(sumTF.getText()));
                                debitTransaction.setPostDate(LocalDateTime.now());
                                claimsAccountRepo.findById(((ClaimsAccount) listOfAccountsCB.getSelectedItem()).getId()).ifPresent(claimsAccount -> {
                                    debitTransaction.setClaimsAccount(claimsAccount);
                                    debitTransactionRepo.save(debitTransaction);
                                    claimsAccount.getDebitTransactions().add(debitTransaction);
                                    claimsAccountRepo.save(claimsAccount);
                                });
                                JOptionPane.showMessageDialog(TransactionFrame.this, "Operacja pomyślnie dodane. Zasada podwójnego księgowania została spełniona", "Infomacja", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean isDouble(String string)
    {
        try
        {
            Double.parseDouble(string);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

}
