package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Producto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;

@Repository
public class ProductorDAO{
    
    private JdbcTemplate database;

    public ProductorDAO(JdbcTemplate database) {
        this.database = database;
    }
  
    public Producto getProductByName(int id){
        return database.queryForObject("SELECT * FROM consumidor WHERE id_producto = ?",new ProductMapper(),id);
    }

    public List<Producto> getProductsOrderedAsc(){
        return database.query("SELECT * FROM producto ORDER BY precio ASC",new ProductMapper());
    }

    public List<Producto> getProductsOrderedDesc(){
        return database.query("SELECT * FROM producto ORDER BY precio DESC",new ProductMapper());
    }


}
