package com.smartTrade.backend.controlllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.daos.*;
import com.smartTrade.backend.models.Comprador;

import jakarta.annotation.PostConstruct;

@RestController
public class CompradorController {

    @Autowired
    CompradorDAO compradorDAO;

    @GetMapping("/comprador/")
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

    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password) {

        Random random = new Random();
        int id = -1;
        Comprador comprador;
        boolean satisfied = false;

        do{
            try{
                id = random.nextInt(1000);
                comprador = compradorDAO.getCompradorByID(id);
            }catch(EmptyResultDataAccessException e){
                satisfied = true;
            }
        }while(satisfied == false);
        try{
            compradorDAO.insertCompradorOnlyNicknameAndPassword(id, nickname, password);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Usuario registrado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al registrar el usuario."));
        }
    }

    @DeleteMapping("/comprador/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "id", required = true) int  id) {

        //// EL METODO BORRAR COMPRADOR HAY QUE BORRAR TODO LO QUE REFERENCIA A ESA ID DE COMPRADOR
            compradorDAO.deleteComprador(id);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario eliminado correctamente."));
    }
}
