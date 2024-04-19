package com.smartTrade.backend.controlllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.daos.UsuarioDAO;
import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.User_Types;
import com.smartTrade.backend.models.Usuario;

@RestController
public class UsuarioController {
    
    @Autowired
    UsuarioDAO usuarioDAO;


    @GetMapping("/user/")
    private ResponseEntity<?> login(@RequestParam(name = "identifier", required = true) String identiFier,
                                    @RequestParam(name = "password", required = true) String password) {
       User_Types userType = usuarioDAO.whatTypeIs(identiFier, password);

       switch(userType){
              case ADMINISTRADOR:
                AdminController admin = new AdminController();
                 return admin.loginAdministrador(identiFier, password);
              case VENDEDOR:
              VendedorController vendor = new VendedorController();
                return ResponseEntity.ok(vendor.getVendedor(identiFier, password));
              case COMPRADOR:
              CompradorController comprador = new CompradorController();
                return ResponseEntity.ok(comprador.login(identiFier, password));
              default:
                return ResponseEntity.badRequest().body("Los parámetros ingresados no corresponden a un usuario existente en la base de datos.");
       }
    }
}

