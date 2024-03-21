package com.smartTrade.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BackendApplication {

        @GetMapping("/mensaje")
        
        public String mensaje(){
            return "Hello World";
        }
        
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
