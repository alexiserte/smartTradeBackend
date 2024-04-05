package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.CompradorMapper;
import com.smartTrade.backend.models.Comprador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class Carrito_CompraDAO{
    
    private JdbcTemplate database;

    public Carrito_CompraDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(int id_comprador) {
        database.update("INSERT INTO Carrito_Compra(id_comprador) VALUES (?)",id_comprador);
    }
    

}
