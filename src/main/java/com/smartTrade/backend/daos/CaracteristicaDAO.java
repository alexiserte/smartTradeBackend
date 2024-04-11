package com.smartTrade.backend.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.mappers.CaracteristicaMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.smartTrade.backend.models.Caracteristica;

@Repository
public class CaracteristicaDAO {
    private JdbcTemplate database;

    public CaracteristicaDAO (JdbcTemplate database) {
        this.database = database;
    }

    public void create(String nombre, String characteristicName) {
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        database.update("INSERT INTO Caracteristica(nombre, id_categoria) VALUES (?, ?);", nombre, id_categoria);
    }

    public Caracteristica readOne(String nombre, String characteristicName) {
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        return database.queryForObject("SELECT nombre, id_categoria FROM Caracteristica WHERE nombre = ? AND id_categoria = ?", new CaracteristicaMapper(), nombre, id_categoria);
    }

    public List<Caracteristica> readAll() {
        return database.query("SELECT nombre, id_categoria FROM Caracteristica", new CaracteristicaMapper());
    }

    public void update(String nombre, String characteristicName, HashMap<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
        for (String key : keys) {
            int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_categoria = ?;", (Integer) valor, nombre, id_categoria);
            } else if (valor instanceof String) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_categoria = ?;", (String) valor, nombre, id_categoria);
            } else if (valor instanceof Double) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_categoria = ?;", (Double) valor, nombre, id_categoria);
            }
        }
    }

    public void delete(String nombre, String characteristicName, String vendorName) {
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        database.update("DELETE FROM Caracteristica WHERE nombre = ? AND id_categoria = ?;", nombre, id_categoria);
    }
    
    public HashMap<String,String> getCaracteristicas(String characteristicName) {
        HashMap<String,String> res  = new HashMap<>();
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        List<Caracteristica> queryResult = database.query("SELECT nombre, id_categoria FROM caracteristica WHERE id_categoria = ?", new CaracteristicaMapper(), id_categoria);
        for(Caracteristica c : queryResult) {
            res.put(c.getNombre(), c.getValor());
        }
        return res;
    }

}
