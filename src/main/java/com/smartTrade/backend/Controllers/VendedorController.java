package com.smartTrade.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Fachada.VendedorFachada;

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
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "direccion", required = true) String dirección,
            @RequestParam(value = "mail", required = true) String mail) {
        return fechada.registerVendedor(nickname, password, dirección, mail);
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
