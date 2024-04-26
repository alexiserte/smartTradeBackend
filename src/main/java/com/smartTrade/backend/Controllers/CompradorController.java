package com.smartTrade.backend.Controllers;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Fachada.CompradorFachada;;

@RestController
public class CompradorController {

    @Autowired
    CompradorFachada mediador;


    @GetMapping("/comprador/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
                                  @RequestParam(value = "password", required = false) String password) {
        return mediador.login(identifier, password);
    }

    
    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestParam(value = "password", required = true) String password,
                                     @RequestParam(value = "correo", required = true) String correo,
                                     @RequestParam(value = "direccion", required = true) String direccion,
                                     @RequestBody HashMap<String, ?> body) {
        return mediador.register(password, correo, direccion, body);
    }


    @DeleteMapping("/comprador/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "nickname", required = true) String nickname) {
        return mediador.deleteComprador(nickname);
    }


    @PutMapping("/comprador/")
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = true) String nickname,
                                           @RequestParam(value = "password", required = false) String password,
                                           @RequestParam(value = "correo", required = false) String correo,
                                           @RequestParam(value = "direccion", required = false) String direccion,
                                           @RequestParam(value = "puntos_responsabilidad", required = false) Integer puntos_responsabilidad) {
        return mediador.updateComprador(nickname, password, correo, direccion, puntos_responsabilidad);
    }

   
    @GetMapping("/comprador/productos_comprados/")
    public ResponseEntity<?> getProductsBought(@RequestParam(value = "nickname", required = true) String nickname) {
        return mediador.productosComprados(nickname);
    }

}
