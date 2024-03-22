package com.smartTrade.backend.repos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.smartTrade.backend.models.Producto;

@Repository
public class ProductoRepository {
    
    private JdbcTemplate database;

    public ProductoRepository(JdbcTemplate database) {
        this.database = database;
    }

    
}
