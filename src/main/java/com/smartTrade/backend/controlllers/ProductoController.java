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
import com.smartTrade.backend.models.Vendedor;
import com.smartTrade.backend.utils.StringComparison;

@RestController
public class ProductoController {

    @Autowired
    ProductoDAO productoDAO;

    @Autowired
    VendedorDAO vendedorDAO;

    @Autowired
    PrecioDAO precioDAO;

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
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "vendor", required = true) String vendorName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        try {
            productoDAO.update(nombre, vendorName, atributos);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);

        }catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @SuppressWarnings("unused")
    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestParam(name = "vendor", required = true) int id_vendedor) {
        try {
            String vendorName = vendedorDAO.getVendorName(id_vendedor);
            List<Object> resultado = productoDAO.readOne(productName, vendorName);

            class Resultado {
                Producto producto;
                HashMap<String, String> smartTag;
                String vendedor;
                String categoria;

                public Resultado(Producto producto, HashMap<String, String> smartTag, String vendedor,String categoria) {
                    this.producto = producto;
                    this.smartTag = smartTag;
                    this.vendedor = vendedor;
                    this.categoria = categoria;
                }

                public Producto getProducto() {
                    return producto;
                }

                public HashMap<String, String> getSmartTag() {
                    return smartTag;
                }

                public String getCategoria() {
                    return categoria;
                }

                public String getVendedor() {
                    return vendedor;
                }

            }

            @SuppressWarnings("unchecked")
            Resultado r = new Resultado((Producto) resultado.get(0), (HashMap<String, String>) resultado.get(1),vendorName,(String) resultado.get(2));
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

    @SuppressWarnings("unused")
    @GetMapping("/producto/estadisticas/")
    public ResponseEntity<?> getEstadisticas(@RequestParam(name = "name", required = true) String productName,
        @RequestParam(name = "vendor", required = true) String vendorName){
            try{
                Vendedor vendedor = vendedorDAO.readOne(vendorName);
                try{
                    Producto producto = productoDAO.readOneProduct(productName, vendedor.getNickname());
                    HashMap<String,?> mapaCaracteristicas = precioDAO.getStats(producto.getNombre(), vendedor.getNickname());

                    class ProductoEstadisticas{
                        String producto;
                        String vendedor;
                        java.util.HashMap<String,?> estadisticas;

                        public ProductoEstadisticas(String producto, String vendedor,java.util.HashMap<String,?> estadisticas){
                            this.producto = producto;
                            this.vendedor = vendedor;
                            this.estadisticas = estadisticas;
                        }

                        public String getProducto(){
                            return producto;
                        }

                        public String getVendedor(){
                            return vendedor;
                        }

                        public java.util.HashMap<String,?> getEstadisticas(){
                            return estadisticas;
                        }
                    }

                    ProductoEstadisticas pe = new ProductoEstadisticas(producto.getNombre(),vendedor.getNickname(), mapaCaracteristicas);
                    return new ResponseEntity<>(pe,HttpStatus.OK);
                }catch(EmptyResultDataAccessException e){
                    return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
                }catch(Exception e){
                    return new ResponseEntity<>("Error al obtener el producto", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("Vendedor no econtrado", HttpStatus.NOT_FOUND);
            }catch(Exception e){
                return new ResponseEntity<>("Error al obtener el vendedor", HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        
        }

}
