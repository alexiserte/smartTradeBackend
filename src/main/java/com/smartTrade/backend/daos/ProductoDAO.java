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
            database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ? AND id IN(SELECT id_usuario FROM Vendedor)) AND id_categoria IN(SELECT id FROM Categoria WHERE nombre = ?)", new ProductMapper(), nombre, vendorName, characteristicName);
        }catch(EmptyResultDataAccessException e){
            int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?", Integer.class, characteristicName);
            int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
            database.update("INSERT INTO Producto(nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido) VALUES (?, ?, ?, ?, ?,?,?);", nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fechaSQL);
        }
    }

    public List<Object> readOne(String productName, String vendorName) {
        
        List<Object> res = new ArrayList<>();
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?))", Integer.class, productName, vendorName);

        Producto producto = database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion, imagen,fecha_añadido FROM Producto WHERE id = ?", new ProductMapper(), id_producto);
        
       
        HashMap<String,String> caracteristicas = CaracteristicaDAO.getSmartTag(productName, vendorName);

        res.add(0,producto);

        res.add(1,caracteristicas);

        return res;
    }

    public List<Producto> readAll() {
        return database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido FROM Producto", new ProductMapper());
    }

    public void update(String nombre, String vendorName, HashMap<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
        int id_vendedor = database.queryForObject("SELECT id_vendedor FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?))", Integer.class, nombre, vendorName);
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
        }
    }

    public void delete(String nombre, String vendorName) {
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
        database.update("DELETE FROM Producto WHERE nombre = ? AND id_vendedor = ?;", nombre, id_vendedor);
    }


    public List<Producto> getProductsByCategory(String categoryName) {
        return database.query("SELECT nombre,id_vendedor,id_categoria,descripcion,precio,imagen,fecha_añadido FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE nombre = ?)", new ProductMapper(), categoryName);
    }

    public List<Producto> getProductsBySeller(String vendorName) {
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        return database.query("SELECT nombre,id_vendedor,id_categoria,descripcion,precio,imagen,fecha_añadido FROM Producto WHERE id_vendedor IN (SELECT id FROM Vendedor WHERE id_usuario IN (SELECT id FROM Usuario WHERE nickname = ?))", new ProductMapper(), id_vendedor);
    }

    public boolean isFromOneCategory(String productName, int id_vendedor, String categoryName) {
    
            return database.queryForObject("SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id_vendedor = ? AND id_categoria IN(SELECT id FROM Categoria WHERE nombre = ?)",Integer.class, productName, id_vendedor, categoryName) != 0;
            
    }



}
