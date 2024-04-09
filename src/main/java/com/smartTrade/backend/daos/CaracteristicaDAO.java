package com.smartTrade.backend.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import com.smartTrade.backend.mappers.CaracteristicaMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.smartTrade.backend.models.Caracteristica;

public class CaracteristicaDAO {
    private JdbcTemplate database;

    public CaracteristicaDAO(JdbcTemplate database) {
        this.database = database;
    }

    public void create(String nombre, int id_producto, String valor, int id_categoria) {
        database.update("INSERT INTO Caracteristica(nombre, id_producto, valor, id_categoria) VALUES (?, ?, ?, ?);", nombre, id_producto, valor, id_categoria);
    }

    public Caracteristica readOne(String nombre, int id_producto) {
        return database.queryForObject("SELECT nombre, valor, id_categoria, id_producto FROM Caracteristica WHERE nombre = ? AND id_producto = ?", new CaracteristicaMapper(), nombre, id_producto);
    }

    public List<Caracteristica> readAll() {
        return database.query("SELECT nombre, valor, id_categoria, id_producto FROM Caracteristica", new CaracteristicaMapper());
    }

    public void update(String nombre, int id_producto, HashMap<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
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

    public void delete(String nombre, int id_producto) {
        database.update("DELETE FROM Caracteristica WHERE nombre = ? AND id_producto = ?;", nombre, id_producto);
    }
    
    public HashMap<String,String> getCaracteristicas(int id_producto) {
        HashMap<String,String> res  = new HashMap<>();
        List<Caracteristica> queryResult = database.query("SELECT nombre, valor, id_categoria, id_producto FROM caracteristica WHERE id_producto = ?", new CaracteristicaMapper(), id_producto);
        for(Caracteristica c : queryResult) {
            res.put(c.getNombre(), c.getValor());
        }
        return res;
    }

}
