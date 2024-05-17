package com.smartTrade.backend.Controllers;

import com.smartTrade.backend.Logger.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartTrade.backend.Fachada.VendedorFachada;

import java.util.HashMap;

@RestController
public class VendedorController {

    @Autowired
    VendedorFachada fechada;

    private Logger logger = Logger.getInstance();

    @GetMapping("/vendedor/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier, @RequestParam(value = "password", required = false) String password, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.getVendedor(identifier, password);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @GetMapping("/vendedor/productos/")
    public ResponseEntity<?> getVendedorProductos(@RequestParam(value = "identifier", required = true) String identifier, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.getProductsFromOneVendor(identifier);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    
    @PostMapping("/vendedor/")
    public ResponseEntity<?> register(@RequestBody HashMap<String, ?> body, HttpServletRequest request) {
        if (!body.containsKey("nickname") || !body.containsKey("user_password") || !body.containsKey("correo")
                || !body.containsKey("direccion")){
            ResponseEntity<?> res = ResponseEntity.badRequest().body("Faltan campos obligatorios");
            logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
            return res;
        }
        ResponseEntity<?> res = fechada.registerVendedor(body);
        logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;}
    
    @DeleteMapping("/vendedor/")
    public ResponseEntity<?> deleteVendedor(@RequestParam(value = "nickname", required = true) String nickname, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.deleteVendedor(nickname);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @PutMapping("/vendedor/")
    public ResponseEntity<?> updateVendedor(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "direccion", required = false) String dirección,
            @RequestParam(value = "mail", required = false) String mail, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.updateVendedor(nickname, password, dirección, mail);
        logger.logRequestAndResponse(HttpMethod.PUT, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    
}
