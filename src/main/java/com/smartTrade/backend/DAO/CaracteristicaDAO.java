package com.smartTrade.backend.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartTrade.backend.Mappers.CaracteristicaMapper;
import com.smartTrade.backend.Mappers.Categoria_CaracteristicaMapper;
import com.smartTrade.backend.Models.Caracteristica;
import com.smartTrade.backend.Models.Categoria_Caracteristica;

import lombok.experimental.SuperBuilder;

@Repository
public class CaracteristicaDAO implements DAOInterface<Categoria_Caracteristica>{
    private JdbcTemplate database;

    public CaracteristicaDAO (JdbcTemplate database) {
        this.database = database;
    }

    public void create(Object ...args) {
        String nombre = (String) args[0];
        String characteristicName = (String) args[1];
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        database.update("INSERT INTO Caracteristicas_Categoria(nombre, id_categoria) VALUES (?, ?);", nombre, id_categoria);
    }

    public Categoria_Caracteristica readOne(Object ...args) {
        String nombre = (String) args[0];
        String characteristicName = (String) args[1];
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        return database.queryForObject("SELECT nombre, id_categoria FROM Caracteristicas_Categoria WHERE nombre = ? AND id_categoria = ?", new Categoria_CaracteristicaMapper(), nombre, id_categoria);
    }

    public List<Categoria_Caracteristica> readAll() {
        return database.query("SELECT nombre, id_categoria FROM Caracteristicas_Categoria", new Categoria_CaracteristicaMapper());
    }

    @SuppressWarnings("unchecked")
    public void update(Object ...args) {
        String nombre = (String) args[0];
        String characteristicName = (String) args[1];
        Map<String, Object> atributos = (HashMap<String, Object>) args[2];
        List<String> keys = new ArrayList<>(atributos.keySet());
        for (String key : keys) {
            int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Caracteristicas_Categoria SET " + key + " = ? WHERE nombre = ? AND id_categoria = ?;", (Integer) valor, nombre, id_categoria);
            } else if (valor instanceof String) {
                database.update("UPDATE Caracteristicas_Categoria SET " + key + " = ? WHERE nombre = ? AND id_categoria = ?;", (String) valor, nombre, id_categoria);
            } else if (valor instanceof Double) {
                database.update("UPDATE Caracteristicas_Categoria SET " + key + " = ? WHERE nombre = ? AND id_categoria = ?;", (Double) valor, nombre, id_categoria);
            }
        }
    }

    public void delete(Object ...args) {
        String nombre = (String) args[0];
        String characteristicName = (String) args[1];
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        database.update("DELETE FROM Caracteristicas_Categoria WHERE nombre = ? AND id_categoria = ?;", nombre, id_categoria);
    }
    
    public List<String> getCaracteristicasFromOneCategoria(String categoryName) {
        List<String> res  = new ArrayList<>();
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, categoryName);
        List<Categoria_Caracteristica> queryResult = database.query("SELECT nombre, id_categoria FROM Caracteristicas_Categoria WHERE id_categoria = ?", new Categoria_CaracteristicaMapper(), id_categoria);
        for(Categoria_Caracteristica c : queryResult) {
            res.add(c.getNombre());
        }
        return res;
    }

}
