package com.smartTrade.backend.daos;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuardarMasTardeDAO{
    
    private JdbcTemplate database;

    public GuardarMasTardeDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(String compradorName) {
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Guardar_Mas_Tarde(id_comprador) VALUES (?)",id_comprador);
    }
    

}
