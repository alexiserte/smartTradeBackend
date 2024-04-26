package com.smartTrade.backend.controlllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.DAO.*;
import com.smartTrade.backend.Factory.ConverterFactory;
import com.smartTrade.backend.Factory.ProductFactory;
import com.smartTrade.backend.Models.Product_Types;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.Vendedor;
import com.smartTrade.backend.utils.StringComparison;
import com.smartTrade.backend.utils.PNGConverter;

@RestController
public class ProductoController {

    @Autowired
    ProductoDAO productoDAO;

    @Autowired
    VendedorDAO vendedorDAO;

    @Autowired
    PrecioDAO precioDAO;
    

    ConverterFactory factory = new ConverterFactory();
    PNGConverter converter = (PNGConverter) factory.createConversor("PNG");
    private final String DEFAULT_IMAGE = converter.processData("src/main/resources/default_image.png");

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
                    .filter(producto -> !productoDAO.isFromOneCategory(producto.getNombre(),
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
    public ResponseEntity<?> insertarProducto(
            @RequestBody(required = true) Map<String,?> body) {
        
            try {
                String imageToAdd;
                if(body.containsKey("imagen")){
                    imageToAdd = (String) body.get("imagen");
                }
                else{
                    imageToAdd = DEFAULT_IMAGE;
                }
                String nombre = (String) body.get("nombre");
                String vendorName = (String) body.get("vendedor");
                double precio = (double) body.get("precio");
                String descripcion = (String) body.get("descripcion");
                String characteristicName = (String) body.get("categoria");
                productoDAO.create(nombre, characteristicName, vendorName, precio, descripcion, imageToAdd);
                return new ResponseEntity<>(HttpStatus.CREATED);
            
            
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseEntity.status(400).body(e.getMessage()));
        }
    }



    @DeleteMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> deleteProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName) {
        
        try {
            productoDAO.deleteProduct(productName, vendorName);
            return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/producto/")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "name", required = true) String nombre) {
        try {
            productoDAO.delete(nombre);
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
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        try {
            productoDAO.update(nombre, atributos);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);

        }catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> updateProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName,
            @RequestBody(required = true) HashMap<String, ?> atributos) {
        try {
            productoDAO.updateProductFromOneVendor(productName, vendorName, atributos);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestParam(name = "image", required = true) boolean image) {
        try {
            
                List<Object> resultado = productoDAO.readOne(productName);

                class Resultado {
                    Producto producto;
                    HashMap<String, String> smartTag;
                    String categoria;
                    Map<String, Double> vendedores;
                    String imagen;

                    public Resultado(Producto producto, HashMap<String, String> smartTag, String categoria,
                            Map<String, Double> vendedores,String image) {
                        this.producto = producto;
                        this.smartTag = smartTag;
                        this.categoria = categoria;
                        this.vendedores = vendedores;
                        this.imagen = image;
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

                    public Map<String, Double> getVendedores() {
                        return vendedores;
                    }

                    public String getImage(){
                        return imagen;
                    }
                }

                String imagenProducto = "";
                if(image){
                    imagenProducto = productoDAO.getImageFromOneProduct(productName);
                }
                @SuppressWarnings("unchecked")
                Resultado r = new Resultado((Producto) resultado.get(0), (HashMap<String, String>) resultado.get(1),
                        (String) resultado.get(2), (Map<String, Double>) resultado.get(3),imagenProducto);
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
            Producto producto = productoDAO.readOneProduct(nombre);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al validar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            productoDAO.validate(nombre);
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
    public ResponseEntity<?> getEstadisticas(@RequestParam(name = "name", required = true) String productName){
            try{
                try{
                    Producto producto = productoDAO.readOneProduct(productName);
                    TreeMap<String,Object> mapaCaracteristicas = precioDAO.getStats(producto.getNombre());

                    class ProductoEstadisticas{
                        String producto;
                        java.util.TreeMap<String,?> estadisticas;

                        public ProductoEstadisticas(String producto,java.util.TreeMap<String,?> estadisticas){
                            this.producto = producto;
                            this.estadisticas = estadisticas;
                        }

                        public String getProducto(){
                            return producto;
                        }


                        public java.util.TreeMap<String,?> getEstadisticas(){
                            return estadisticas;
                        }
                    }

                    ProductoEstadisticas pe = new ProductoEstadisticas(producto.getNombre(), mapaCaracteristicas);
                    return new ResponseEntity<>(pe,HttpStatus.OK);
                }catch(EmptyResultDataAccessException e){
                    return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
                }catch(Exception e){
                    return new ResponseEntity<>("| Error al obtener el producto: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("Vendedor no econtrado", HttpStatus.NOT_FOUND);
            }catch(Exception e){
                return new ResponseEntity<>("|| Error al obtener el vendedor: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        
        }

}
