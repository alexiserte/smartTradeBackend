package com.smartTrade.backend.DAO;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuardarMasTardeDAO implements DAOInterface<Object>{
    
    private JdbcTemplate database;

    public GuardarMasTardeDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(Object ...args) {
        String compradorName = (String) args[0];
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Guardar_Mas_Tarde(id_comprador) VALUES (?)",id_comprador);
    }

    public void delete(Object ...args) {;}
    public void update(Object ...args) {;}
    public Object readOne(Object ...args) {return null;}
    public List<Object> readAll() {return null;}
    

}
