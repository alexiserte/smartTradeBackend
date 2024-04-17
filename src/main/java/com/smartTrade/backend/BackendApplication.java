package com.smartTrade.backend;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smartTrade.backend.factory.ProductFactory;
import com.smartTrade.backend.models.Moda;
import com.smartTrade.backend.models.Product_Types;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

            ProductFactory pf = new ProductFactory();
            Moda moda = (Moda) pf.getProduct(Product_Types.MODA,"user",3,43.2,"hola",2,"imagen",java.sql.Date.valueOf("2024-02-01"),true,2, List.of((Object)"a",(Object)"b",(Object)"c",(Object)"d",(Object)"e"));
            System.out.println(moda.getClass().getSimpleName());
        
    }

}
