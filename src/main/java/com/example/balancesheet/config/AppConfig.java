package com.example.balancesheet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
public class AppConfig {

    AccountNames accountNames = new AccountNames();


    public AccountNames getAccountNames() {
        return accountNames;
    }

    public void setAccountNames(AccountNames accountNames) {
        this.accountNames = accountNames;
    }

}
