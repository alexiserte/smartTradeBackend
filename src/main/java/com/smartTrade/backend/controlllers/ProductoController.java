package com.smartTrade.backend.controlllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.daos.*;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.utils.StringComparison;


@RestController
public class ProductoController {

    @Autowired
    ProductoDAO productoDAO;

    @GetMapping("/productos/")
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "name", required = true) String nombre, @RequestParam(name = "category", required = false) String category) {
        List<Producto> todosLosProductos = productoDAO.readAll();
        List<Producto> res = todosLosProductos
                .stream()
                .filter(producto -> StringComparison.areSimilar(nombre, producto.getNombre()))
                .toList();
        if (category != null) {
            res = res.stream()
                    .filter(producto -> productoDAO.isFromOneCategory(producto.getNombre(),producto.getId_vendedor(),category))
                    .toList();
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PostMapping("/producto/")
    public ResponseEntity<?> insertarProducto(@RequestParam(name = "name", required = true) String nombre,
                                                @RequestParam(name = "vendor", required = true) String vendorName,
                                                @RequestParam(name = "price", required = true) double precio,
                                                @RequestParam(name = "description", required = true) String descripcion,
                                                @RequestParam(name = "category", required = true) String characteristicName,
                                                @RequestParam(name = "image", required = true) String imagen)
    {
        try{
            productoDAO.create(nombre,characteristicName, vendorName, precio, descripcion,imagen);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body(e.getMessage()));
        }
    }

    @DeleteMapping("/producto/")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "name", required = true) String nombre,
                                           @RequestParam(name = "vendor", required = true) String vendorName) {
        try {
            productoDAO.delete(nombre,vendorName);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al eliminar el producto: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/producto/")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "nombre", required = true) String nombre,
                                           @RequestParam(name = "vendor", required = true) String vendorName,
                                           @RequestParam(name = "attributes", required = true) HashMap<String, ?> atributos) {
        try {
            productoDAO.update(nombre, vendorName, atributos);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al actualizar el producto: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @SuppressWarnings("unused")
    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
                                       @RequestParam(name = "vendor", required = true) String vendorName) {
        try {
            List<?> resultado = productoDAO.readOne(productName, vendorName);

            class Response{
                public Producto producto;
                public HashMap<String,String> smartTag;

                public Response(Producto producto, HashMap<String,String> smartTag){
                    this.producto = producto;
                    this.smartTag = smartTag;
                }

                public Producto getProduct(){
                    return this.producto;
                }

                public HashMap<String,String> getSmartTag(){
                    return this.smartTag;
                }


            }

            Response r = new Response((Producto) resultado.get(0), (HashMap<String,String>) resultado.get(1));
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener el producto: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

