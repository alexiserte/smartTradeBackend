package com.smartTrade.backend.Controllers;

import com.smartTrade.backend.Logger.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartTrade.backend.Facada.UsuarioFachada;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioFachada fechada;

    private Logger logger = Logger.getInstance();

    @GetMapping("/user/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
                                   @RequestParam(value = "password", required = false) String password, HttpServletRequest request) {
        ResponseEntity<?> res =  fechada.login(identifier, password);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @PostMapping("/user/")
    public ResponseEntity<?> register(@RequestBody(required = true) String body, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.register(body);
        logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
   
}
