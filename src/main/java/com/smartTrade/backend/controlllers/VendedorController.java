package com.smartTrade.backend.controlllers;

import java.util.HashMap;
import java.util.Map;
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
import com.smartTrade.backend.models.Vendedor;

@RestController
public class VendedorController {

    @Autowired
    VendedorDAO vendedorDAO;

    @GetMapping("/vendedor/")
    public ResponseEntity<?> getVendedor(@RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "password", required = false) String password) {
        if(password == null){ // Si no se envía la contraseña, se asume que se quiere obtener la información del usuario
            try{
                Vendedor vendedor = vendedorDAO.readOne(identifier);
                return ResponseEntity.ok(vendedor);
            }catch(Exception e){
                return ResponseEntity.ok(ResponseEntity.status(400).body("Error al obtener el usuario."));
            }
        }
        else{ // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Vendedor vendedor = vendedorDAO.readOne(identifier);
                if (vendedor.getPassword().equals(password)) {
                    return ResponseEntity.ok(vendedor);
                }
                return ResponseEntity.ok(ResponseEntity.status(400).body("Contraseña incorrecta."));
            } catch (EmptyResultDataAccessException e) {
                return ResponseEntity.ok(ResponseEntity.status(404).body("Usuario no encontrado."));
            } 
        }
    }

    @PostMapping("/vendedor/")
    public ResponseEntity<?> registerVendedor(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo,
            @RequestParam(value = "direccion", required = true) String direccion){
        try{
            vendedorDAO.create(nickname, password,correo,direccion);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Usuario registrado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al registrar el usuario. MOTIVO: " + e.getMessage()));
        }
    }

    @DeleteMapping("/vendedor/")
    public ResponseEntity<?> deleteVendedor(@RequestParam(value = "nickname", required = true) String  nickname) {
        try{    
            vendedorDAO.delete(nickname);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario eliminado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al eliminar el usuario."));
        }
    }

    @PutMapping("/vendedor/")
    public ResponseEntity<?> updateVendedor(@RequestParam(value = "nickname", required = true) String nickname,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "direccion", required = false) String dirección,
                                            @RequestParam(value = "mail", required = false) String mail)
    {
        try{
            Map<String,Object> attributes = new HashMap<>();
            if(password == null && dirección == null && mail == null ){
                return ResponseEntity.ok(ResponseEntity.status(400).body("No se ha enviado ningún atributo para actualizar."));
            }
            if(password != null){
                attributes.put("user_password", password);
            }
            if(dirección != null){
                attributes.put("direccion", dirección);
            }
            if(mail != null){
                attributes.put("correo", mail);
            }

            vendedorDAO.update(nickname, attributes);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario actualizado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al actualizar el usuario."));
        } 
    }
}
