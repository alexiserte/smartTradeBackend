package com.smartTrade.backend.controlllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "category", required = false) String category) {
        List<Producto> resultado = new ArrayList<>();
        List<Producto> todosLosProductos = productoDAO.readAll();
        List<Producto> res = todosLosProductos
                .stream()
                .filter(producto -> StringComparison.areSimilar(nombre, producto.getNombre()))
                .toList();
        if (category != null) {
            resultado = todosLosProductos
                    .stream()
                    .filter(producto -> StringComparison.areSimilar(nombre, producto.getNombre()))
                    .filter(producto -> !productoDAO.isFromOneCategory(producto.getNombre(), producto.getId_vendedor(),
                            category))
                    .toList();
            if (resultado.size() == 0) {
                return new ResponseEntity<>("No se han encontrado productos que cumplen con los criterios de búsqueda",
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(resultado, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(res, HttpStatus.OK);

        }
    }

    @PostMapping("/producto/")
    public ResponseEntity<?> insertarProducto(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "vendor", required = true) String vendorName,
            @RequestParam(name = "price", required = true) double precio,
            @RequestParam(name = "description", required = true) String descripcion,
            @RequestParam(name = "category", required = true) String characteristicName,
            @RequestParam(name = "image", required = true) String imagen) {
        
            try {
            try{
                productoDAO.readOne(imagen, vendorName);
                return new ResponseEntity<>("El producto ya existe", HttpStatus.CONFLICT);
            }
            catch(EmptyResultDataAccessException e){
                productoDAO.create(nombre, characteristicName, vendorName, precio, descripcion, imagen);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseEntity.status(400).body(e.getMessage()));
        }
    }

    @DeleteMapping("/producto/")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "vendor", required = true) String vendorName) {
        try {
            productoDAO.delete(nombre, vendorName);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/producto/")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "vendor", required = true) String vendorName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        try {
            productoDAO.update(nombre, vendorName, atributos);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>("Los valores son iguales a los ya existentes. No se ha actualizado el producto",
                    HttpStatus.OK);
        } 
        catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @SuppressWarnings("unused")
    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestParam(name = "vendor", required = true) String vendorName) {
        try {
            List<Object> resultado = productoDAO.readOne(productName, vendorName);

            class Resultado {
                Producto producto;
                HashMap<String, String> smartTag;

                public Resultado(Producto producto, HashMap<String, String> smartTag) {
                    this.producto = producto;
                    this.smartTag = smartTag;
                }

                public Producto getProducto() {
                    return producto;
                }

                public HashMap<String, String> getSmartTag() {
                    return smartTag;
                }

            }

            @SuppressWarnings("unchecked")
            Resultado r = new Resultado((Producto) resultado.get(0), (HashMap<String, String>) resultado.get(1));
            return ResponseEntity.ok(r);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    @PutMapping("/producto/validar/")
    public ResponseEntity<?> validarProducto(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "vendor", required = true) String vendorName) {
        try {
            Producto producto = productoDAO.readOneProduct(nombre, vendorName);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al validar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            productoDAO.validate(nombre, vendorName);
            return new ResponseEntity<>("Producto validado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("El producto ya ha sido validado", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al validar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
