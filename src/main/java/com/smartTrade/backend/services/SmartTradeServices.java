package com.smartTrade.backend.services;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smartTrade.backend.models.*;
import java.util.Random;

import com.smartTrade.backend.daos.*;

@RestController
public class SmartTradeServices {
    
    @Autowired
    CompradorDAO compradorDAO;


    @GetMapping("/services/login/")
    public ResponseEntity<?> login(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password) {

        try {
            Comprador comprador = compradorDAO.getCompradorByNicknameAndPassword(nickname, password);
            return ResponseEntity.ok(comprador);
        } catch (EmptyResultDataAccessException e) {
            try {
                compradorDAO.getCompradorByNombre(nickname); // Intenta obtener al comprador solo por nickname
                return ResponseEntity.ok(ResponseEntity.status(400).body("Contraseña incorrecta."));
            } catch (EmptyResultDataAccessException e2) {
                return ResponseEntity.ok(ResponseEntity.status(404).body("Usuario no encontrado."));
           }
        }
    }

    @PostMapping("/services/register/")
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password) {

        Random random = new Random();
        int id = random.nextInt(1000);
        try{
            compradorDAO.insertCompradorOnlyNicknameAndPassword(id, nickname, password);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Usuario registrado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al registrar el usuario."));
        }
    }

    @PutMapping("/services/change-password/")
    public ResponseEntity<?> changePassword(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "oldPassword", required = true) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword) {

        try {
            Comprador comprador = compradorDAO.getCompradorByNicknameAndPassword(nickname, oldPassword);
            compradorDAO.changeCompradorPassword(nickname, newPassword);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Contraseña actualizada correctamente."));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(ResponseEntity.status(400).body("Contraseña incorrecta."));
        }
    }

}
