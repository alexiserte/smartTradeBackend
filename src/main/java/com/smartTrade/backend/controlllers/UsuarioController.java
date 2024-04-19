package com.smartTrade.backend.controlllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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

       switch(userType){
              case ADMINISTRADOR:
                Administrador administrador = admin.readOne(identiFier);
                if(administrador.getPassword().equals(password)){
                    MultiValueMap<String, String> headerHashMap = new LinkedMultiValueMap<>();
                    headerHashMap.add("userType",User_Types.ADMINISTRADOR.toString());
                    return ResponseEntity.ok(administrador);
                }
              case VENDEDOR:
                Vendedor vendedor = vendedorDAO.readOne(identiFier);
                if(vendedor.getPassword().equals(password)){
                    MultiValueMap<String, String> headerHashMap = new LinkedMultiValueMap<>();
                    headerHashMap.add("userType",User_Types.VENDEDOR.toString());
                    return ResponseEntity.ok(vendedor);
                }
              case COMPRADOR:
                Comprador comprador = compradorDAO.readOne(identiFier);
                if(comprador.getPassword().equals(password)){
                    MultiValueMap<String, String> headerHashMap = new LinkedMultiValueMap<>();
                    headerHashMap.add("userType",User_Types.COMPRADOR.toString());
                    return ResponseEntity.ok(comprador);
                }
              default:
                return ResponseEntity.badRequest().body("Los parámetros ingresados no corresponden a un usuario existente en la base de datos.");
       }
    }
}

