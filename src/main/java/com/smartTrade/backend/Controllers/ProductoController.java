package com.smartTrade.backend.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartTrade.backend.Fachada.ProductoFachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    public ResponseEntity<?> insertarProducto(@RequestBody(required = true) String body) throws JsonProcessingException {
        return fachada.insertarProducto(body);
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

        if (!oldMode) return fachada.getProduct(productName, image);
        else return fachada.getOldProduct(productName);
    }

    @GetMapping("/productos/vendedor/")
    public ResponseEntity<?> getProductsFromOneVendor(@RequestParam(name = "identifier", required = true) String vendorName) {
        return fachada.getProductsFromOneVendor(vendorName);
    }


    @PutMapping("/producto/validar/")
    public ResponseEntity<?> validateProduct(@RequestParam(name = "name", required = true) String productName, @RequestParam(name = "vendor", required = true) String vendor) {
        return fachada.validarProducto(productName, vendor);
    }


    @GetMapping("/producto/estadisticas/")
    public ResponseEntity<?> getStatistics(@RequestParam(name = "name", required = true) String productName) {
        return fachada.getEstadisticas(productName);
    }

    @GetMapping("/producto/imagen/")
    public ResponseEntity<?> getImage(@RequestParam(name = "name", required = true) String productName) {
        return fachada.getImagen(productName);
    }

    @GetMapping("/productos/nombres")
    public ResponseEntity<?> getProductsNames() {
        return fachada.productAllNames();
    }

    @GetMapping("/producto/stock/")
    public ResponseEntity<?> getstock(@RequestParam(name = "productName", required = true) String productName,
                                      @RequestParam(name = "vendorName", required = true) String vendorName) {
        return fachada.getStockFromOneVendor(productName, vendorName);
    }

}
