package com.smartTrade.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.models.*;
import java.util.List;
import java.util.ArrayList;
import com.smartTrade.backend.daos.*;

@SpringBootApplication
@RestController
public class BackendApplication {

        @Autowired
        CompradorDAO compradorDAO;

        @GetMapping("/")
        public String mensaje(){
            return "Hello World";
        }

        @GetMapping("/five-guys")
        public List<MiembroEquipo> equipo(){
            MiembroEquipo m1 = new MiembroEquipo("Alejandro", "Iserte");
            MiembroEquipo m2 = new MiembroEquipo("Laura", "Illán");
            MiembroEquipo m3 = new MiembroEquipo("Carlos", "Ibáñez");
            MiembroEquipo m4 = new MiembroEquipo("Sergio", "Martí");
            MiembroEquipo m5 = new MiembroEquipo("Jennifer", "López");
            
            List<MiembroEquipo> res = new ArrayList<>();
            res.add(m1);
            res.add(m2);
            res.add(m3);
            res.add(m4);
            res.add(m5);

            return res;
        }


        @GetMapping("/comprador/?id={id}")
        public Comprador comprador(@PathVariable String id){
            return compradorDAO.getCompradorByID(Integer.parseInt(id));
        }
        
        
        
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
