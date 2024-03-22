package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.CompradorMapper;
import com.smartTrade.backend.models.Comprador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompradorDAO{
    
    private JdbcTemplate database;

    public CompradorDAO(JdbcTemplate database) {
        this.database = database;
    }

    public Comprador getCompradorByID(int id){
        return database.queryForObject("SELECT * FROM consumidor WHERE id_consumidor = ?",new CompradorMapper(),id);
    }

}
