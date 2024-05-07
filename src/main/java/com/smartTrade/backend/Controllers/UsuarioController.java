package com.smartTrade.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartTrade.backend.Fachada.UsuarioFachada;

import java.util.HashMap;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioFachada fechada;

    @GetMapping("/user/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
                                  @RequestParam(value = "password", required = false) String password) {
        return fechada.login(identifier, password);
    }

    @PostMapping("/user/")
    public ResponseEntity<?> register(@RequestBody(required = true) String body){
        return fechada.register(body);
    }
   
}
