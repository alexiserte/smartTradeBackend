package com.smartTrade.backend.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Models.MiembroEquipo;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class BasicController {
    
    @GetMapping("/")
    public String mensaje(HttpServletRequest request) {
        System.out.println(" -- Nueva conexión registrada -- Hora: " + LocalDateTime.now());
        return "¡Bienvenido a smartTrade!";
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Servidor en funcionamiento", HttpStatus.OK);
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
}
