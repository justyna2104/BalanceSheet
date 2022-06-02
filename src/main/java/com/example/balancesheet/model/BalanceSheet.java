package com.example.balancesheet.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "balanceSheet")
public class BalanceSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GenericGenerator(name = "native",strategy = "native")
    private long id;

    @Column(name = "entityName")
    private String entityName;

    @Column(name = "assetSum")
    private Double assetSum = 0.0;

    @Column(name = "claimsSum")
    private Double claimsSum = 0.0;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "balanceSheet")
    private Set<AssetAccount> assets = new HashSet<AssetAccount>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "balanceSheet")
    private Set<ClaimsAccount> claims = new HashSet<ClaimsAccount>();

    public BalanceSheet(String entityName) {
        this.entityName = entityName;
    }

    public BalanceSheet() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<AssetAccount> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetAccount> assets) {
        this.assets = assets;
    }

    public Set<ClaimsAccount> getClaims() {
        return claims;
    }

    public void setClaims(Set<ClaimsAccount> claims) {
        this.claims = claims;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Double getAssetSum() {
        return assetSum;
    }

    public void setAssetSum(Double assetSum) {
        this.assetSum = assetSum;
    }

    public Double getClaimsSum() {
        return claimsSum;
    }

    public void setClaimsSum(Double claimsSum) {
        this.claimsSum = claimsSum;
    }
}
