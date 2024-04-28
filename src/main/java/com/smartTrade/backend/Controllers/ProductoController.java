package com.smartTrade.backend.Controllers;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Fachada.ProductoFachada;

@RestController
public class ProductoController {

    @Autowired
    ProductoFachada fechada;


    @GetMapping("/productos/")
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "category", required = false) String category) {
        return fechada.searchProductByName(nombre, category);
    }



    @PostMapping("/producto/")
    public ResponseEntity<?> insertarProducto(@RequestBody(required = true) HashMap<String, ?> body) {
        return fechada.insertarProducto(body);
    }



    @DeleteMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> deleteProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName) {
        
        return fechada.deleteProductFromOneVendor(productName, vendorName);
    }
    

    @DeleteMapping("/producto/")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "name", required = true) String productName) {
        return fechada.deleteProduct(productName);
    }
    

    @PutMapping("/producto/")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        return fechada.updateProduct(productName, atributos);
    }
    

    @PutMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> updateProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        return fechada.updateProductFromOneVendor(productName, vendorName, atributos);
    }


    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestParam(name = "image", required = true) boolean image){
        return fechada.getProduct(productName,image);
    }



    @PutMapping("/producto/validar/")
    public ResponseEntity<?> validateProduct(@RequestParam(name = "name", required = true) String productName,@RequestParam(name = "vendor", required = true) String vendor){
        return fechada.validarProducto(productName,vendor);
    }
   

  
    @GetMapping("/producto/estadisticas/")
    public ResponseEntity<?> getStatistics(@RequestParam(name = "name", required = true) String productName){
        return fechada.getEstadisticas(productName);
    }

    @GetMapping("/qr/")
    public ResponseEntity<?> qr(@RequestParam(name = "name", required = true) String name) {
        System.out.println("Generando QR para el producto: " + name);
        return fechada.qr(name);
    }

    @GetMapping("/qr/update")
    public ResponseEntity<?> qr_update() {
        System.out.println("Generando QR");
        return fechada.qr_update();
    }
    

}
