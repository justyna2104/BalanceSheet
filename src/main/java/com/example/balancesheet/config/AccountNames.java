package com.example.balancesheet.config;

import java.util.ArrayList;
import java.util.List;

public class AccountNames {

    List<String> assets = new ArrayList<>();
    List<String> claims = new ArrayList<>();

    public List<String> getAssets() {
        return assets;
    }

    public void setAssets(List<String> assets) {
        this.assets = assets;
    }

    public List<String> getClaims() {
        return claims;
    }

    public void setClaims(List<String> claims) {
        this.claims = claims;
    }

}
