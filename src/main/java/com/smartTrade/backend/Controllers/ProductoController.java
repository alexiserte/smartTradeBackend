package com.smartTrade.backend.Controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jsqlparser.expression.TryCastExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.OffsetScrollPositionHandlerMethodArgumentResolverCustomizer;
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
    ProductoFachada fachada;


    @GetMapping("/productos/")
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "category", required = false) String category) {
        return fachada.searchProductByName(nombre, category);
    }



    @PostMapping("/producto/")
    public ResponseEntity<?> insertarProducto(@RequestBody(required = true) Object body) throws JsonProcessingException {
        try {
            String bodyString = (String) body;
            return fachada.insertarProducto(bodyString);
        } catch (Exception e) {
            LinkedHashMap<String, String> response = new LinkedHashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            return fachada.insertarProducto(objectMapper.writeValueAsString(response));
        }
    }



    @DeleteMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> deleteProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName) {
        
        return fachada.deleteProductFromOneVendor(productName, vendorName);
    }
    

    @DeleteMapping("/producto/")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "name", required = true) String productName) {
        return fachada.deleteProduct(productName);
    }
    

    @PutMapping("/producto/")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        return fachada.updateProduct(productName, atributos);
    }
    

    @PutMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> updateProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        return fachada.updateProductFromOneVendor(productName, vendorName, atributos);
    }


    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestParam(name = "image", required = true) boolean image,
                                        @RequestParam(name = "oldMode", required = false) boolean oldMode) {

        if(!oldMode)   return fachada.getProduct(productName,image);
        else           return fachada.getOldProduct(productName);
    }

    @GetMapping("/productos/vendedor/")
    public ResponseEntity<?> getProductsFromOneVendor(@RequestParam(name = "identifier", required = true) String vendorName){
        return fachada.getProductsFromOneVendor(vendorName);
    }



    @PutMapping("/producto/validar/")
    public ResponseEntity<?> validateProduct(@RequestParam(name = "name", required = true) String productName,@RequestParam(name = "vendor", required = true) String vendor){
        return fachada.validarProducto(productName,vendor);
    }
   

  
    @GetMapping("/producto/estadisticas/")
    public ResponseEntity<?> getStatistics(@RequestParam(name = "name", required = true) String productName){
        return fachada.getEstadisticas(productName);
    }

    @GetMapping("/producto/imagen/")
    public ResponseEntity<?> getImage(@RequestParam(name = "name", required = true) String productName){
        return fachada.getImagen(productName);
    }

    @GetMapping("/productos/nombres")
    public ResponseEntity<?> getProductsNames(){
        return fachada.productAllNames();
    }

    

}
