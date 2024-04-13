package com.smartTrade.backend.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.mappers.CategoriaMapper;
import com.smartTrade.backend.models.Categoria;

@Repository
public class CategoriaDAO {

    private JdbcTemplate database;

    public CategoriaDAO(JdbcTemplate database) {
        this.database = database;
    }

    public void create(String nombre, String categoria_principal) {
        database.update("INSERT INTO Categoria(nombre, categoria_principal) VALUES (?, ?);", nombre, categoria_principal);
    }

    public void delete(String nombre) {
        database.update("DELETE FROM Categoria WHERE nombre = ?;", nombre);
    }

    public void update(String nombre, HashMap<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
        for (String key : keys) {
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                if(key.equals("categoria_principal")){
                    database.update("UPDATE Categoria SET " + key + " = (SELECT id FROM Categoria WHERE nombre = ?) WHERE nombre = ?;", (String) valor, nombre);
                }
                database.update("UPDATE Categoria SET " + key + " = ? WHERE nombre = ?;", (Integer) valor, nombre);
            } else if (valor instanceof String) {
                if(key.equals("categoria_principal")){
                    database.update("UPDATE Categoria SET " + key + " = (SELECT id FROM Categoria WHERE nombre = ?) WHERE nombre = ?;", (String) valor, nombre);
                } 
                database.update("UPDATE Categoria SET " + key + " = ? WHERE nombre = ?", (String) valor, nombre);
            } else if (valor instanceof Double) {
                if(key.equals("categoria_principal")){
                    database.update("UPDATE Categoria SET " + key + " = (SELECT id FROM Categoria WHERE nombre = ?) WHERE nombre = ?;", (String) valor, nombre);
                }
                database.update("UPDATE Categoria SET " + key + " = ? WHERE nombre = ?", (Double) valor, nombre);
            }
        }    
    }

    public Categoria readOne(String nombre) {
        return database.queryForObject("SELECT nombre, categoria_principal FROM Categoria WHERE nombre = ?", new CategoriaMapper(), nombre);
    }

    public List<Categoria> readAll() {
        return database.query("SELECT nombre, categoria_principal FROM Categoria", new CategoriaMapper());
    }

    public int getID(String nombre) {
        return database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?", Integer.class, nombre);
    }
}
