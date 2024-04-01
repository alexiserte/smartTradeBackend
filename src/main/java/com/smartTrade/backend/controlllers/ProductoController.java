package com.smartTrade.backend.controlllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.daos.*;
import com.smartTrade.backend.models.Comprador;


@RestController
public class ProductoController {

    @Autowired
    ProductoDAO productoDAO;

    @GetMapping("/producto/")
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "descripcion", required = false) String descripcion,
                                                  @RequestParam(name = "vendorName", required = false) String vendorName) 
    {
        if (descripcion != null) {
            return ResponseEntity.ok(productoDAO.searchProductByName(descripcion));
        } else if (vendorName != null) {
            return ResponseEntity.ok(productoDAO.getProductsFromOneVendor(vendorName));
        } else {
            return ResponseEntity.ok(productoDAO.getAllProducts());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertarProducto(@RequestParam(name = "nombre", required = true) String nombre,
                                                @RequestParam(name = "id_vendedor", required = true) int idVendedor,
                                                @RequestParam(name = "precio", required = true) double precio)
    {
        try{
            productoDAO.insertProduct(nombre,idVendedor,precio);
            return ResponseEntity.ok(ResponseEntity.status(201).body("Producto añadido correctamente"));
        }catch( Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al insertar un nuevo producto."));
        }
    }
    
}

