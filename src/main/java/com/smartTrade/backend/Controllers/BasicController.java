package com.smartTrade.backend.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.smartTrade.backend.Logger.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Models.MiembroEquipo;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class BasicController {
    private Logger logger = Logger.getInstance();
    @GetMapping("/")
    public String mensaje(HttpServletRequest request) {
        System.out.println(" -- Nueva conexión registrada -- Hora: " + LocalDateTime.now());
        String res =  "¡Bienvenido a smartTrade!";
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res);
        return res;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(HttpServletRequest request) {
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), "Servidor en funcionamiento");
        return new ResponseEntity<>("Servidor en funcionamiento", HttpStatus.OK);
    }

    @GetMapping("/five-guys")
    public List<MiembroEquipo> equipo(HttpServletRequest request) {
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

        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());

        return res;
    }
}
