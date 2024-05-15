package com.smartTrade.backend.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Mappers.CategoriaMapper;
import com.smartTrade.backend.Models.Categoria;

@Repository
public class CategoriaDAO implements DAOInterface<Categoria>{

    private JdbcTemplate database;

    public CategoriaDAO(JdbcTemplate database) {
        this.database = database;
    }

    public void create(Object... args) {
        String nombre = (String) args[0];
        String categoria_principal = (String) args[1];
        database.update("INSERT INTO Categoria(nombre, categoria_principal) VALUES (?, ?);", nombre, categoria_principal);
    }

    public void delete(Object... args) {
        String nombre = (String) args[0];
        database.update("DELETE FROM Categoria WHERE nombre = ?;", nombre);
    }

    @SuppressWarnings("unchecked")
    public void update(Object... args) {
        String nombre = (String) args[0];
        HashMap<String, Object> atributos = (HashMap<String, Object>) args[1];
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

    public Categoria readOne(Object... args) {
        String nombre = (String) args[0];
        return database.queryForObject("SELECT nombre, categoria_principal FROM Categoria WHERE nombre = ?", new CategoriaMapper(), nombre);
    }

    public List<Categoria> readAll() {
        return database.query("SELECT nombre, categoria_principal FROM Categoria ORDER BY id", new CategoriaMapper());
    }

    public int getIDFromName(String nombre){
        try{
            return database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?", Integer.class, nombre);
        }catch(EmptyResultDataAccessException e){
            return -1;
        }
    }

    public String getNameFromID(int id){
        try{
            return database.queryForObject("SELECT nombre FROM Categoria WHERE id = ?", String.class, id);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
