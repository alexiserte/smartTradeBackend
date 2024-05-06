package com.smartTrade.backend.Fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.DAO.ProductoDAO;
import com.smartTrade.backend.Models.Vendedor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.Factory.ConverterFactory;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Utils.*;

@Component
public class ProductoFachada extends Fachada {
    // Se usa solo en un método pero se podria usar más adelante
    private final String DEFAULT_IMAGE = "src/main/resources/default_image.png";

    public ResponseEntity<?> searchProductByName(String nombre, String category) {
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

    public ResponseEntity<?> insertarProducto(String bodyJSON) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> body = null;
        try {
            body = objectMapper.readValue(bodyJSON, new TypeReference<HashMap<String,Object>>(){});
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de procesamiento JSON: " + e.getMessage());
        }

        PNGConverter converter = new PNGConverter();
        try {
            String imageToAdd = body.containsKey("imagen") ? (String) body.get("imagen") : converter.convertFileToBase64(DEFAULT_IMAGE);
            String nombre = (String) body.get("nombre");
            String vendorName = (String) body.get("vendedor");
            double precio = ((Number) body.get("precio")).doubleValue();
            String descripcion = (String) body.get("descripcion");
            String characteristicName = (String) body.get("categoria");

            // Convertir los argumentos al tipo correcto
            Object[] arguments = {nombre, characteristicName, vendorName, precio, descripcion, imageToAdd};

            // Llamar a create con los argumentos convertidos
            productoDAO.create(arguments);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }





    public ResponseEntity<?> deleteProductFromOneVendor(String productName, String vendorName) {
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

    public ResponseEntity<?> deleteProduct(String nombre) {
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

    public ResponseEntity<?> updateProduct(String nombre, HashMap<String, ?> atributos) {
        try {
            productoDAO.update(nombre, atributos);
            return new ResponseEntity<>("Producto actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> updateProductFromOneVendor(String productName, String vendorName,
            HashMap<String, ?> atributos) {
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
    public ResponseEntity<?> getProduct(String productName, boolean image) {
        try {
            
                List<Object> resultado = productoDAO.readOne(productName);

                class Resultado {
                    Producto producto;
                    HashMap<String, String> smartTag;
                    String categoria;
                    Map<String, Double> vendedores;
                    String imagen;

                    public Resultado(Producto producto, HashMap<String, String> smartTag, String categoria,
                            Map<String, Double> vendedores, String imagen) {
                        this.producto = producto;
                        this.smartTag = smartTag;
                        this.categoria = categoria;
                        this.vendedores = vendedores;
                        this.imagen = imagen;
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

                    public String getImagen() {
                        return imagen;
                    }
                }

                String imagen = "";
                if (image) {
                    imagen = productoDAO.getImageFromOneProduct(productName);
                }


                @SuppressWarnings("unchecked")
                Resultado r = new Resultado((Producto) resultado.get(0), (HashMap<String, String>) resultado.get(1),
                        (String) resultado.get(2), (Map<String, Double>) resultado.get(3),imagen);
                return ResponseEntity.ok(r);
            

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el producto: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> validarProducto(String nombre, String vendorName) {
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
    public ResponseEntity<?> getEstadisticas(String productName){
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

    public ResponseEntity<?> qr(String name) {
        System.out.println("Generando QR para el producto: " + name);
        return new ResponseEntity<>(smartTagDAO.createSmartTag(name), HttpStatus.OK);
    }

    public ResponseEntity<?> qr_update() {
        productoDAO.updateSmartTag();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getProductsFromOneVendor(String vendorName) {
        try {
            Vendedor vendedor = vendedorDAO.readOne(vendorName);
            List<Producto> productos = productoDAO.getProductsBySeller(vendedor.getNickname());
            class Producto_Precio{
                private Producto producto;
                private double precio;

                public Producto_Precio(Producto producto, double precio){
                    this.producto = producto;
                    this.precio = precio;
                }

                public Producto getProducto(){
                    return producto;
                }

                public double getPrecio(){
                    return precio;
                }
            }
            List<Producto_Precio> productosConPrecio = new ArrayList<>();
            for (Producto producto : productos) {
                double precio = productoDAO.getPrecioProducto(vendorName, producto.getNombre());
                productosConPrecio.add(new Producto_Precio(producto, precio));
            }
            return new ResponseEntity<>(productosConPrecio, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Vendedor no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los productos del vendedor: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getOldProduct(String productName) {
        try {
            ProductoDAO.ProductoAntiguo producto = productoDAO.readOneProductAntiguo(productName);

            List<Object> resultado = productoDAO.readOne(productName);
            Producto productNewVersion = (Producto) resultado.get(0);
            HashMap<String, String> smartTag = (HashMap<String, String>) resultado.get(1);
            String categoria = (String) resultado.get(2);
            Map<String, Double> vendedores = (Map<String, Double>) resultado.get(3);

            class Resultado {
                ProductoDAO.ProductoAntiguo producto;
                HashMap<String, String> smartTag;
                String categoria;
                Map<String, Double> vendedores;
                String imagen;

                public Resultado(ProductoDAO.ProductoAntiguo producto, HashMap<String, String> smartTag, String categoria,
                                 Map<String, Double> vendedores) {
                    this.producto = producto;
                    this.smartTag = smartTag;
                    this.categoria = categoria;
                    this.vendedores = vendedores;
                    this.imagen = producto.getImagen();
                }

                public ProductoDAO.ProductoAntiguo getProducto() {
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

                public String getImagen() {
                    return imagen;
                }
            }
            @SuppressWarnings("unchecked")
            Resultado r = new Resultado(producto, smartTag, categoria, vendedores);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el producto: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }}
            public ResponseEntity<?> getImagen (String productName){
                try {
                    String imagen = productoDAO.getImageFromOneProduct(productName);
                    return new ResponseEntity<>(imagen, HttpStatus.OK);
                } catch (EmptyResultDataAccessException e) {
                    return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
                } catch (Exception e) {
                    return new ResponseEntity<>("Error al obtener la imagen del producto: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

                }
            }

            public ResponseEntity<?> productAllNames(){
                try{
                    List<String> nombres = productoDAO.readAllNames();
                    return new ResponseEntity<>(nombres, HttpStatus.OK);
                }catch(Exception e){
                    return new ResponseEntity<>("Error al obtener los nombres de los productos: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        }


