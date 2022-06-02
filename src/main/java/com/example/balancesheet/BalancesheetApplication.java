package com.example.balancesheet;

import com.example.balancesheet.swingView.GetEntityNameFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BalancesheetApplication implements CommandLineRunner {

    @Autowired
    private GetEntityNameFrame getEntityNameFrame;

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(BalancesheetApplication.class);

        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);
    }

    @Override
    public void run(String... args) {
        getEntityNameFrame.onCreate();
    }
}
