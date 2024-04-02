package com.smartTrade.backend.controlllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al iniciar sesión. MOTIVO: " + e.getMessage()));
        }
        
    }

    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo) {
        try{
            compradorDAO.insertComprador(nickname, password,correo);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Usuario registrado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al registrar el usuario. MOTIVO: " + e.getMessage()));
        }
    }

    @DeleteMapping("/comprador/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "id", required = true) int  id) {

        //// EL METODO BORRAR COMPRADOR HAY QUE BORRAR TODO LO QUE REFERENCIA A ESA ID DE COMPRADOR
            compradorDAO.deleteComprador(id);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario eliminado correctamente."));
    }

    @PutMapping("/comprador/")
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = false) String nickname,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "direccion", required = false) String dirección,
                                            @RequestParam(value = "puntos_responsabilidad", required = false) int puntosResponsabilidad)
    {
        try{
            return null;
            /*
             * 
             * AQUÍ HAY CREAR UN BLOQUE DE IF EN EL QUE VAYA LLAMANDO A LOS MÉTODOS DE ACTUALIZACIÓN DE LA BASE DE DATOS
             * Si hay más de un parámetro a actualizar, no hay que crear solo un méotodo que actualice todo, sino que hay que llamar a los métodos de actualización de la base de datos por
             * separado
             * 
            */
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al actualizar el usuario."));
        } 
    }
}
