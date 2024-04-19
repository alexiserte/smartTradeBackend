package com.smartTrade.backend.controlllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.daos.AdministradorDAO;
import com.smartTrade.backend.daos.CompradorDAO;
import com.smartTrade.backend.daos.UsuarioDAO;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.mappers.VendedorMapper;
import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.User_Types;
import com.smartTrade.backend.models.Usuario;
import com.smartTrade.backend.models.Vendedor;

@RestController
public class UsuarioController {
    
    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    CompradorDAO compradorDAO;

    @Autowired
    AdministradorDAO admin;

    @Autowired
    VendedorDAO vendedorDAO;

    @GetMapping("/user/")
    private ResponseEntity<?> login(@RequestParam(name = "identifier", required = true) String identiFier,
                                    @RequestParam(name = "password", required = true) String password) {
       User_Types userType = usuarioDAO.whatTypeIs(identiFier, password);

       class Response<T>{
            private T user;
            private User_Types userType;

            public Response(T user, User_Types userType){
                this.user = user;
                this.userType = userType;
            }

            public T getUser(){
                return user;
            }

            public User_Types getUserType(){
                return userType;
            }
       }
       switch(userType){
              case ADMINISTRADOR:
                Administrador administrador = admin.readOne(identiFier);
                if(administrador.getPassword().equals(password)){
                    Response<Administrador> response = new Response<>(administrador, User_Types.ADMINISTRADOR);
                    return ResponseEntity.ok(response);
                }
                else{
                    return new ResponseEntity<>("Contraseña incorrecta",HttpStatus.UNAUTHORIZED);
                }
              case VENDEDOR:
                Vendedor vendedor = vendedorDAO.readOne(identiFier);
                if(vendedor.getPassword().equals(password)){
                    Response<Vendedor> response = new Response<>(vendedor, User_Types.VENDEDOR);
                    return ResponseEntity.ok(response);
                }
                else{
                    return new ResponseEntity<>("Contraseña incorrecta",HttpStatus.UNAUTHORIZED);
                }
              case COMPRADOR:
                Comprador comprador = compradorDAO.readOne(identiFier);
                if(comprador.getPassword().equals(password)){
                    Response<Comprador> response = new Response<>(comprador, User_Types.COMPRADOR);
                    return ResponseEntity.ok(response);
                }
                else{
                    return new ResponseEntity<>("Contraseña incorrecta",HttpStatus.UNAUTHORIZED);
                }
              default:
                return new ResponseEntity<>("Nombre de usuario incorrecto",HttpStatus.NOT_FOUND);
       }
    }
}

