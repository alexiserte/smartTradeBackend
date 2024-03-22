package com.smartTrade.backend.repos;
import com.smartTrade.backend.DAO.CompradorDAO;
import com.smartTrade.backend.models.Comprador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompradorDAO{
    
    private JdbcTemplate database;

    public CompradorRepository(JdbcTemplate database) {
        this.database = database;
    }

    public Comprador getCompradorByID(int id){
        return database.queryForObject("SELECT * FROM consumidor WHERE id_consumidor = ?",new CompradorDAO(),id);
    }

}
