package com.smartTrade.backend;


import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("smartTradeBackend started!");
        System.out.println(CountriesMethods.getCountriesListInAlphabeticalWithEmojis());
        Logger logger = Logger.getInstance();
        logger.logSystem("smartTradeBackend started!");
    }

}
