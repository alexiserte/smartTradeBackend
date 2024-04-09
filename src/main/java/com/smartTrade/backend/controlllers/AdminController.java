package com.smartTrade.backend.controlllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartTrade.backend.daos.AdministradorDAO;
import com.smartTrade.backend.daos.CompradorDAO;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.Vendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    AdministradorDAO admin;

    @Autowired
    CompradorDAO comprador;

    @Autowired
    VendedorDAO vendedor;

    @GetMapping("/admin/database")
    public List<String> mostrarBasesDeDatos() {
        return admin.getAllDatabases();
    }

    @GetMapping("/admin/comprador")
    public List<Comprador> mostrarUsuarios() {
        return comprador.readAll();
    }

    @GetMapping("/admin/vendedor")
    public List<Vendedor> mostrarVendedores() {
        return vendedor.readAll();
    }

    @GetMapping("/admin/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "password", required = false) String password) {
        if(password == null){ // Si no se envía la contraseña, se asume que se quiere obtener la información del usuario
            try{
                Administrador administrador = admin.readOne(identifier);
                return ResponseEntity.ok(comprador);
            }catch(Exception e){
                return ResponseEntity.ok(ResponseEntity.status(400).body("Error al obtener el usuario."));
            }
        }
        else{ // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Administrador administrador = admin.readOne(identifier);
                if (administrador.getPassword().equals(password)) {
                    return ResponseEntity.ok(administrador);
                }
                return ResponseEntity.ok(ResponseEntity.status(400).body("Contraseña incorrecta."));
            }catch (EmptyResultDataAccessException e) {
                return ResponseEntity.ok(ResponseEntity.status(404).body("Usuario no encontrado."));
            } 
        }
    }

    @PostMapping("/admin/")
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo,
            @RequestParam(value = "direccion", required = true) String direccion){
        try{
            admin.create(nickname, password,correo,direccion);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Usuario registrado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al registrar el usuario. MOTIVO: " + e.getMessage()));
        }
    }

    @PutMapping("/admin/")
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = false) String nickname,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "direccion", required = false) String dirección)
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
            admin.update(nickname, attributes);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario actualizado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al actualizar el usuario."));
        } 
    }

    @DeleteMapping("/admin/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "nickname", required = true) String  nickname) {
        try{    
        admin.delete(nickname);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario eliminado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al eliminar el usuario."));
        }
    }
}

