package com.smartTrade.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Fachada.UsuarioFachada;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioFachada fechada;

    @GetMapping("/user/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
                                  @RequestParam(value = "password", required = false) String password) {
        return fechada.login(identifier, password);
    }
   
}
