package com.smartTrade.backend.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.mappers.CaracteristicaMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.smartTrade.backend.models.Caracteristica;
import com.smartTrade.backend.models.Categoria;

@Repository
public class Caracteristica_ProductoDAO {
    private JdbcTemplate database;

    public Caracteristica_ProductoDAO(JdbcTemplate database) {
        this.database = database;
    }

    public void create(String nombre, String productName, String vendorName, String valor, String characteristicName) {
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        database.update("INSERT INTO Caracteristica(nombre, id_producto, valor, id_categoria) VALUES (?, ?, ?, ?);", nombre, id_producto, valor, id_categoria);
    }

    public Caracteristica readOne(String nombre, String productName, String vendorName) {
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        return database.queryForObject("SELECT nombre, valor, id_categoria, id_producto FROM Caracteristica WHERE nombre = ? AND id_producto = ?", new CaracteristicaMapper(), nombre, id_producto);
    }

    public List<Caracteristica> readAll() {
        return database.query("SELECT nombre, valor, id_categoria, id_producto FROM Caracteristica", new CaracteristicaMapper());
    }

    public void update(String nombre, String productName, String vendorName, HashMap<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        for (String key : keys) {
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_producto = ?;", (Integer) valor, nombre, id_producto);
            } else if (valor instanceof String) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_producto = ?;", (String) valor, nombre, id_producto);
            } else if (valor instanceof Double) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_producto = ?;", (Double) valor, nombre, id_producto);
            }
        }
    }

    public void delete(String nombre, String productName, String vendorName) {
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        database.update("DELETE FROM Caracteristica WHERE nombre = ? AND id_producto = ?;", nombre, id_producto);
    }
    
    public HashMap<String,String> getSmartTag(String productName, String vendorName){
        HashMap<String,String> res = new HashMap<>();
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = ANY(SELECT id_usuario FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?))", Integer.class, productName, vendorName);

        List<Caracteristica> queryResult = database.query("SELECT id_caracteristica, valor, id_categoria, id_producto FROM Caracteristica WHERE id_producto = ?", new CaracteristicaMapper(), id_producto);
        
        List<Object> valores = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        for(Caracteristica c : queryResult) {
            valores.add(c.getValor());
            String nombre = database.queryForObject("SELECT nombre FROM Caracteristicas_Categoria WHERE id = ?", String.class, c.getId_caracteristica());
            nombres.add(nombre);
        }

        for(int i = 0; i < nombres.size(); i++) {
            res.put(nombres.get(i), valores.get(i).toString());
        }

        res.remove("product");
        return res;
    }
}
