package com.smartTrade.backend.Controllers;

import java.util.HashMap;

import com.smartTrade.backend.Fachada.CarritoCompraFachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartTrade.backend.Fachada.CompradorFachada;;

@RestController
public class CompradorController {

    @Autowired
    CompradorFachada mediador;

    @Autowired
    CarritoCompraFachada carritoCompraFachada;


    @GetMapping("/comprador/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
                                  @RequestParam(value = "password", required = false) String password) {
        return mediador.login(identifier, password);
    }

    
    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestBody HashMap<String, ?> body) {
        if(!body.containsKey("nickname") || !body.containsKey("user_password") || !body.containsKey("correo") || !body.containsKey("direccion"))
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        return mediador.register(body);
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

    @GetMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> getCarritoCompra(@RequestParam(value = "identifier", required = true) String identifier,
                                             @RequestParam(value = "discountCode", required = false) String discountCode) {
        return carritoCompraFachada.getCarritoCompra(identifier, discountCode);
    }

    @PutMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> updateCantidad(@RequestParam(value = "productName", required = true) String productName,
                                            @RequestParam(value = "vendorName", required = true) String vendorName,
                                            @RequestParam(value = "userNickname", required = true) String userNickname,
                                            @RequestParam(value = "op", required = true) String op) {
        return carritoCompraFachada.modificarCantidad(userNickname, productName, vendorName, op);
    }

    @PostMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> addProduct(@RequestParam(value = "productName", required = true) String productName,
                                        @RequestParam(value = "vendorName", required = true) String vendorName,
                                        @RequestParam(value = "userNickname", required = true) String userNickname) {
        return carritoCompraFachada.insertarProducto(productName,vendorName,userNickname);
    }

    @DeleteMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> deleteProduct(@RequestParam(value = "productName", required = true) String productName,
                                           @RequestParam(value = "vendorName", required = true) String vendorName,
                                           @RequestParam(value = "userNickname", required = true) String userNickname) {
        return carritoCompraFachada.deleteProduct(productName, vendorName, userNickname);
    }

}
