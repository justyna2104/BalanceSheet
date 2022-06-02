package com.example.balancesheet.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "claimsAccount")
public class ClaimsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "native",strategy = "native")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "closingSum")
    private double closingSum;

    @ManyToOne
    @JoinColumn(name = "balanceSheetId")
    private BalanceSheet balanceSheet;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "claimsAccount")
    private Set<DebitTransaction> debitTransactions = new HashSet<DebitTransaction>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "claimsAccount")
    private Set<CreditTransaction> creditTransactions = new HashSet<CreditTransaction>();

    public ClaimsAccount(String name, BalanceSheet balanceSheet) {
        this.name = name;
        this.balanceSheet = balanceSheet;
    }

    public ClaimsAccount() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getClosingSum() {
        return closingSum;
    }

    public void setClosingSum(double closingSum) {
        this.closingSum = closingSum;
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public Set<DebitTransaction> getDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(Set<DebitTransaction> debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public Set<CreditTransaction> getCreditTransactions() {
        return creditTransactions;
    }

    public void setCreditTransactions(Set<CreditTransaction> creditTransactions) {
        this.creditTransactions = creditTransactions;
    }

    @Override
    public String toString() {
        return name;
    }
}
