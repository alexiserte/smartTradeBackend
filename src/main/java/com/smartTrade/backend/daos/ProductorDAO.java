package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Producto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import com.smartTrade.utils.StringComparison;

@Repository
public class ProductorDAO{
    
    private JdbcTemplate database;

    public ProductorDAO(JdbcTemplate database) {
        this.database = database;
    }
  
    public List<Producto> searchProductByName(String descripcion){

        List<Producto> res = new ArrayList<>();

        List<Producto> busquedaInicial =  database.query("SELECT * FROM producto",new ProductMapper(),descripcion);

        for(Producto product : busquedaInicial){
            if(StringComparison.areSimilar(product.getDescripcion(), descripcion)){
                res.add(product);
            }
        }

        return res;

    }

    public List<Producto> getProductsOrderedAsc(){
        return database.query("SELECT * FROM producto ORDER BY precio ASC",new ProductMapper());
    }

    public List<Producto> getProductsOrderedDesc(){
        return database.query("SELECT * FROM producto ORDER BY precio DESC",new ProductMapper());
    }

    public List<Producto> searchProductByNameOrderedASC(String descripcion){

        List<Producto> res = new ArrayList<>();

        List<Producto> busquedaInicial =  database.query("SELECT * FROM producto ORDER BY precio ASC",new ProductMapper(),descripcion);

        for(Producto product : busquedaInicial){
            if(StringComparison.areSimilar(product.getDescripcion(), descripcion)){
                res.add(product);
            }
        }

        return res;

    }
    public List<Producto> searchProductByNameOrderedDESC(String descripcion){

        List<Producto> res = new ArrayList<>();

        List<Producto> busquedaInicial =  database.query("SELECT * FROM producto ORDER BY precio DESC",new ProductMapper(),descripcion);

        for(Producto product : busquedaInicial){
            if(StringComparison.areSimilar(product.getDescripcion(), descripcion)){
                res.add(product);
            }
        }
        return res;

    }

    


}
