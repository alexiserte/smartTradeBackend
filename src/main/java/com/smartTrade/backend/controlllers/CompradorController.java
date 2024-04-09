package com.smartTrade.backend.controlllers;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "password", required = false) String password) {
        if(password == null){ // Si no se envía la contraseña, se asume que se quiere obtener la información del usuario
            try{
                Comprador comprador = compradorDAO.readOne(identifier);
                return ResponseEntity.ok(comprador);
            }catch(Exception e){
                return ResponseEntity.ok(ResponseEntity.status(400).body("Error al obtener el usuario."));
            }
        }
        else{ // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Comprador comprador = compradorDAO.readOne(identifier);
                if (comprador.getPassword().equals(password)) {
                    return ResponseEntity.ok(comprador);
                }
                return ResponseEntity.ok(ResponseEntity.status(400).body("Contraseña incorrecta."));
            } catch (EmptyResultDataAccessException e) {
                return ResponseEntity.ok(ResponseEntity.status(404).body("Usuario no encontrado."));
            } 
        }
    }

    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo,
            @RequestParam(value = "direction", required = true) String direccion){
        try{
            compradorDAO.create(nickname, password,correo,direccion);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Usuario registrado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al registrar el usuario. MOTIVO: " + e.getMessage()));
        }
    }

    @DeleteMapping("/comprador/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "nickname", required = true) String  nickname) {
        try{    
        compradorDAO.delete(nickname);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario eliminado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al eliminar el usuario."));
        }
    }

    @PutMapping("/comprador/")
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = false) String nickname,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "direction", required = false) String dirección,
                                            @RequestParam(value = "points", required = false) String puntosResponsabilidad)
    {
        try{
            Map<String,Object> attributes = new HashMap<>();
            if(nickname != null){
                attributes.put("nickname", nickname);
            }
            if(password != null){
                attributes.put("user_password", password);
            }
            if(dirección != null){
                attributes.put("direccion", dirección);
            }
            if(puntosResponsabilidad != null){
                attributes.put("puntos_responsabilidad", Integer.parseInt(puntosResponsabilidad));
            }

            compradorDAO.update(nickname, attributes);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario actualizado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al actualizar el usuario."));
        } 
    }
}
