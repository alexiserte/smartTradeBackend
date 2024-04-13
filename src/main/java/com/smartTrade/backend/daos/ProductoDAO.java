package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.daos.Caracteristica_ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


@Repository
public class ProductoDAO{
    
    private JdbcTemplate database;

    @Autowired
    VendedorDAO VendedorDAO;

    @Autowired
    Caracteristica_ProductoDAO CaracteristicaDAO;

    public ProductoDAO(JdbcTemplate database) {
        this.database = database;
    }
  
    public void create(String nombre, String characteristicName, String vendorName, double precio, String descripcion,String imagen) {
        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
        try{
            database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ? AND id IN(SELECT id_usuario FROM Vendedor)) AND id_categoria IN(SELECT id FROM Categoria WHERE nombre = ?)", new ProductMapper(), nombre, vendorName, characteristicName);
        }catch(EmptyResultDataAccessException e){
            int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?", Integer.class, characteristicName);
            int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
            database.update("INSERT INTO Producto(nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado) VALUES (?, ?, ?, ?, ?,?,?,?);", nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fechaSQL, false);
            database.update("INSERT INTO Pendientes_Validacion(id_producto) SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = ?;", nombre, id_vendedor);
            database.update("INSERT INTO Historico_Precios(id_producto,precio) SELECT id, precio FROM Producto WHERE nombre = ? AND id_vendedor = ?;", nombre, id_vendedor);
        }
    }

    public List<Object> readOne(String productName, String vendorName) {
        
        List<Object> res = new ArrayList<>();
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?))", Integer.class, productName, vendorName);

        Producto producto = database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion, imagen,fecha_añadido,validado FROM Producto WHERE id = ?", new ProductMapper(), id_producto);
        
       
        HashMap<String,String> caracteristicas = CaracteristicaDAO.getSmartTag(productName, vendorName);

        res.add(0,producto);

        res.add(1,caracteristicas);

        return res;
    }

    public Producto readOneProduct(String productName, String vendorName) {
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
        return database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado FROM Producto WHERE nombre = ? AND id_vendedor = ?", new ProductMapper(), productName, id_vendedor);
    }

    public List<Producto> readAll() {
        return database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado FROM Producto", new ProductMapper());
    }

/**
 * El método "actualizar" en Java actualiza los atributos del producto en una base de datos en función
 * de los atributos de entrada y la información del proveedor.
 * 
 * @param nombre El parámetro `nombre` en el método `update` representa el nombre del producto que
 * deseas actualizar en la base de datos. Se utiliza para identificar el producto específico que
 * necesita ser modificado.
 * @param vendorName El parámetro `vendorName` en el método `update` representa el nombre del proveedor
 * asociado con el producto que se actualiza. Se utiliza para identificar al proveedor en la base de
 * datos y recuperar la información del producto correspondiente para actualizarla.
 * @param atributos El parámetro `atributos` en el método `update` es un `HashMap` que contiene pares
 * clave-valor que representan los atributos y sus nuevos valores que deben actualizarse para un
 * producto en la base de datos. Las claves en `HashMap` corresponden a los atributos del producto (por
 * ejemplo, "
 */
    public void update(String nombre, String vendorName, HashMap<String, ?> atributos) throws IllegalArgumentException {
        List<String> keys = new ArrayList<>(atributos.keySet());
        int id_vendedor = database.queryForObject(
                "SELECT id_vendedor FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?))",
                Integer.class, nombre, vendorName);
        Producto product = database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado FROM Producto WHERE nombre = ? AND id_vendedor = ?", new ProductMapper(), nombre, id_vendedor);
        for(String key : keys){
            if(key.equals("nombre")){
                if(atributos.get(key).equals(product.getNombre())){
                    keys.remove(key);
                }
            }else if(key.equals("id_categoria")){
                if(atributos.get(key).equals(product.getId_categoria())){
                    keys.remove(key);
                }
            }
            else if(key.equals("id_vendedor")){
                if(atributos.get(key).equals(product.getId_vendedor())){
                    keys.remove(key);
                }
            }
            else if(key.equals("precio")){
                if(atributos.get(key).equals(product.getPrecio())){
                    keys.remove(key);
                }
            }
            else if(key.equals("descripcion")){
                if(atributos.get(key).equals(product.getDescripcion())){
                    keys.remove(key);
                }
            }
            else if(key.equals("imagen")){
                if(atributos.get(key).equals(product.getImagen())){
                    keys.remove(key);
                }
            }
            else if(key.equals("fecha_añadido")){
                if(atributos.get(key).equals(product.getFecha_publicacion())){
                    keys.remove(key);
                }
            }
            else if(key.equals("validado")){
                if(atributos.get(key).equals(product.getValidado())){
                    keys.remove(key);
                }
            }
        }

        if(keys.size() == 0){
            throw new IllegalArgumentException("Los atributos son iguales a los actuales. No se realizaron cambios.");
        }
        for (String key : keys) {
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ? AND id_vendedor = ?;",
                        (Integer) valor, nombre, id_vendedor);
            } else if (valor instanceof String) {
                database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ? AND id_vendedor = ?;",
                        (String) valor, nombre, id_vendedor);
            } else if (valor instanceof Double) {
                database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ? AND id_vendedor = ?;",
                        (Double) valor, nombre, id_vendedor);
            }
            if (key.equals("precio")) {
                database.update(
                        "INSERT INTO Historico_Precios(id_producto,precio) SELECT id, precio FROM Producto WHERE nombre = ? AND id_vendedor = ?;",
                        nombre, id_vendedor);
            }
        }
    }

    public void delete(String nombre, String vendorName) {
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
        database.update("DELETE FROM Producto WHERE nombre = ? AND id_vendedor = ?;", nombre, id_vendedor);
        database.update("DELETE FROM Historico_Precios WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = ?);", nombre, id_vendedor);
    }


    public List<Producto> getProductsByCategory(String categoryName) {
        return database.query("SELECT nombre,id_vendedor,id_categoria,descripcion,precio,imagen,fecha_añadido, validado FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE nombre = ?)", new ProductMapper(), categoryName);
    }

    public List<Producto> getProductsBySeller(String vendorName) {
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        return database.query("SELECT nombre,id_vendedor,id_categoria,descripcion,precio,imagen,fecha_añadido, validado FROM Producto WHERE id_vendedor IN (SELECT id FROM Vendedor WHERE id_usuario IN (SELECT id FROM Usuario WHERE nickname = ?))", new ProductMapper(), id_vendedor);
    }

    public boolean isFromOneCategory(String productName, int id_vendedor, String categoryName) {
    
            return database.queryForObject("SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id_vendedor = ? AND id_categoria IN(SELECT id FROM Categoria WHERE nombre = ?)",Integer.class, productName, id_vendedor, categoryName) == 0;
            
    }

    public void validate(String nombre, String vendorName) throws EmptyResultDataAccessException{
        int exists = database.queryForObject(
                "SELECT COUNT(*) FROM Pendientes_Validacion WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)))",
                Integer.class, nombre, vendorName);
        if (exists == 0) {
            throw new EmptyResultDataAccessException(1);
        } else {
            int id_vendedor = database.queryForObject(
                    "SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)",
                    Integer.class, vendorName);
            database.update(
                    "DELETE FROM Pendientes_Validacion WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = ?)",
                    nombre, id_vendedor);
            database.update("UPDATE Producto SET validado = true WHERE nombre = ? AND id_vendedor = ?", nombre,
                    id_vendedor);

        }
    }

    public List<Producto> getProductosPendientesDeValidacion() throws Exception{
        List<Producto> productos = database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado FROM Producto WHERE validado = false", new ProductMapper());
        int productosSize = database.queryForObject("SELECT COUNT(*) FROM Pendientes_Validacion", Integer.class);
        if(productosSize == productos.size()){
            return productos;
        }else{
            throw new Exception();
    }
}

}
