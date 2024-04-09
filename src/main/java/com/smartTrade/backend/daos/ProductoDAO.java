package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Caracteristica;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.models.Vendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.smartTrade.backend.utils.*;

@Repository
public class ProductoDAO{
    
    private JdbcTemplate database;

    @Autowired
    VendedorDAO VendedorDAO;

    @Autowired
    CaracteristicaDAO CaracteristicaDAO;

    public ProductoDAO(JdbcTemplate database) {
        this.database = database;
    }
  
    public void create(String nombre, int id_categoria, int id_vendedor, double precio, String descripcion) {
        try{
            database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion FROM Producto WHERE nombre = ? AND id_vendedor = ?;", new ProductMapper(), nombre, id_categoria, id_vendedor, precio, descripcion);
        }catch(EmptyResultDataAccessException e){
            database.update("INSERT INTO Producto(nombre, id_categoria, id_vendedor, precio, descripcion) VALUES (?, ?, ?, ?, ?);", nombre, id_categoria, id_vendedor, precio, descripcion);
        }
    }

    public Map<Producto,HashMap<String,String>> readOne(int id_producto) {
        
        Map<Producto,HashMap<String,String>> res = new HashMap<>();

        Producto producto = database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion FROM Producto WHERE id_producto = ?", new ProductMapper(), id_producto);
        
        HashMap<String,String> caracteristicas = CaracteristicaDAO.getCaracteristicas(id_producto);

        res.put(producto, caracteristicas);

        return res;
    }

    public List<Producto> readAll() {
        return database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion FROM Producto", new ProductMapper());
    }

    public void update(String nombre, int id_vendedor, HashMap<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
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

    public void delete(String nombre, int id_vendedor) {
        database.update("DELETE FROM Producto WHERE nombre = ? AND id_vendedor = ?;", nombre, id_vendedor);
    }


    public List<Producto> getProductsByCategory(String categoryName) {
        return database.query("SELECT nombre,id_vendedor,id_categoria,descripcion,precio FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE nombre = ?)", new ProductMapper(), categoryName);
    }

    public List<Producto> getProductsBySeller(int id_vendedor) {
        return database.query("SELECT * FROM Producto WHERE id_vendedor IN (SELECT id FROM Vendedor WHERE id_usuario IN (SELECT id FROM Usuario WHERE nickname = ?))", new ProductMapper(), id_vendedor);
    }

    public boolean isFromOneCategory(String productName, int id_vendedor, String categoryName) {
        try {
            database.queryForObject("SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id_vendedor = ? AND id_categoria = ANY(SELECT id FROM Categoria WHERE nombre = ?)",Integer.class, productName, id_vendedor, categoryName);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }


}
