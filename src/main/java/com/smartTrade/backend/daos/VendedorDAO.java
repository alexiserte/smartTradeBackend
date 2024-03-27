package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Vendedor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import com.smartTrade.utils.StringComparison;

@Repository
public class VendedorDAO{
    
    private JdbcTemplate database;

    public ProductorDAO(JdbcTemplate database) {
        this.database = database;
    }

    public Vendedor getVendedorByNombre(String nickname){
        return database.query("SELECT id_vendedor,nombre_vendedor FROM vendedor WHERE nombre_vendedor = ?",new VendedorMapper(),nickname);
    }
}
