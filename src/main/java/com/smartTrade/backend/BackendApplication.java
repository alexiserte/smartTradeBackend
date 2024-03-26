package com.smartTrade.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String mensaje() {
        return "Hello World";
    }

    @GetMapping("/five-guys")
    public List<MiembroEquipo> equipo() {
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

    @GetMapping("/comprador/")
    public ResponseEntity<?> comprador(@RequestParam(value = "id", required = false) Integer id_consumidor,
            @RequestParam(value = "nickname", required = false) String nickname) {
        try{
                if (id_consumidor != null && nickname != null) {
            // Lógica para buscar por ID y nombre
            return ResponseEntity.ok(compradorDAO.getCompradorByIDAndNombre(id_consumidor, nickname));
        } else if (id_consumidor != null) {
            // Lógica para buscar por ID
            return ResponseEntity.ok(compradorDAO.getCompradorByID(id_consumidor));
        } else if (nickname != null) {
            // Lógica para buscar por nombre
            return ResponseEntity.ok(compradorDAO.getCompradorByNombre(nickname));
        } else {
            // Manejar escenario sin parámetros
            return ResponseEntity.badRequest().build(); // O lanzar una excepción, dependiendo de tu caso de uso
        }

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
