package com.smartTrade.backend.Controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> insertarProducto(@RequestBody(required = true) Object body) {
        if (body instanceof String) {
            // If the body is a String, directly pass it to insertarProducto method
            return fechada.insertarProducto((String) body);
        } else if (body instanceof LinkedHashMap) {
            // If the body is a LinkedHashMap, assume it's a JSON object
            // Convert it to JSON string
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(body);
                // Now jsonString contains the LinkedHashMap in JSON format
                return fechada.insertarProducto(jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // Handle the exception as needed
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
            }
        } else {
            // Handle unsupported body type here
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported body type");
        }
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
            @RequestParam(name = "image", required = true) boolean image,
                                        @RequestParam(name = "oldMode", required = false) boolean oldMode) {

        if(!oldMode)   return fechada.getProduct(productName,image);
        else           return fechada.getOldProduct(productName);
    }

    @GetMapping("/productos/vendedor/")
    public ResponseEntity<?> getProductsFromOneVendor(@RequestParam(name = "identifier", required = true) String vendorName){
        return fechada.getProductsFromOneVendor(vendorName);
    }



    @PutMapping("/producto/validar/")
    public ResponseEntity<?> validateProduct(@RequestParam(name = "name", required = true) String productName,@RequestParam(name = "vendor", required = true) String vendor){
        return fechada.validarProducto(productName,vendor);
    }
   

  
    @GetMapping("/producto/estadisticas/")
    public ResponseEntity<?> getStatistics(@RequestParam(name = "name", required = true) String productName){
        return fechada.getEstadisticas(productName);
    }

    @GetMapping("/producto/imagen/")
    public ResponseEntity<?> getImage(@RequestParam(name = "name", required = true) String productName){
        return fechada.getImagen(productName);
    }

    @GetMapping("/productos/nombres")
    public ResponseEntity<?> getProductsNames(){
        return fechada.productAllNames();
    }

    

}
