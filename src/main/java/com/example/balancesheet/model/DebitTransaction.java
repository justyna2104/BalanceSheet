package com.example.balancesheet.model;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="debitTransaction")
public class DebitTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "native",strategy = "native")
    @Column(name = "id")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "sum")
    private double sum;

    @Column(name = "postDate")
    private LocalDateTime postDate;

    @ManyToOne
    @JoinColumn(name = "assetAccountId")
    private AssetAccount assetAccount;

    @ManyToOne
    @JoinColumn(name = "claimsAccountId")
    private ClaimsAccount claimsAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public AssetAccount getAssetAccount() {
        return assetAccount;
    }

    public void setAssetAccount(AssetAccount assetAccount) {
        this.assetAccount = assetAccount;
    }

    public ClaimsAccount getClaimsAccount() {
        return claimsAccount;
    }

    public void setClaimsAccount(ClaimsAccount claimsAccount) {
        this.claimsAccount = claimsAccount;
    }
}
