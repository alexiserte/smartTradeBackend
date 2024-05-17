package com.smartTrade.backend;


import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        Logger logger = Logger.getInstance();
        logger.logSystem("smartTradeBackend started!");
    }

}
