package com.smartTrade.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartTrade.backend.Fachada.VendedorFachada;

import java.util.HashMap;

@RestController
public class VendedorController {

    @Autowired
    VendedorFachada fechada;


    @GetMapping("/vendedor/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier, @RequestParam(value = "password", required = false) String password) {
        return fechada.getVendedor(identifier, password);
    }
    

    @GetMapping("/vendedor/productos/")
    public ResponseEntity<?> getVendedorProductos(@RequestParam(value = "identifier", required = true) String identifier) {
        return fechada.getProductsFromOneVendor(identifier);
    }
    

    
    @PostMapping("/vendedor/")
    public ResponseEntity<?> register(@RequestBody HashMap<String, ?> body) {
        if (!body.containsKey("nickname") || !body.containsKey("user_password") || !body.containsKey("correo")
                || !body.containsKey("direccion"))
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        return fechada.registerVendedor(body);
    }
    
    @DeleteMapping("/vendedor/")
    public ResponseEntity<?> deleteVendedor(@RequestParam(value = "nickname", required = true) String nickname) {
        return fechada.deleteVendedor(nickname);
    }

    @PutMapping("/vendedor/")
    public ResponseEntity<?> updateVendedor(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "direccion", required = false) String dirección,
            @RequestParam(value = "mail", required = false) String mail) {
        return fechada.updateVendedor(nickname, password, dirección, mail);
    }
    
}
