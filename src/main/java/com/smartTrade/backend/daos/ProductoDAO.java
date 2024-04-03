package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.models.Vendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import com.smartTrade.backend.utils.*;

@Repository
public class ProductoDAO{
    
    private JdbcTemplate database;

    @Autowired
    VendedorDAO VendedorDAO;

    public ProductoDAO(JdbcTemplate database) {
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

    public List<Producto> getProductByNameOrderedASC(String descripcion){

        List<Producto> res = new ArrayList<>();

        List<Producto> busquedaInicial =  database.query("SELECT * FROM producto ORDER BY precio ASC",new ProductMapper(),descripcion);

        for(Producto product : busquedaInicial){
            if(StringComparison.areSimilar(product.getDescripcion(), descripcion)){
                res.add(product);
            }
        }

        return res;

    }
    public List<Producto> getProductByNameOrderedDESC(String descripcion){

        List<Producto> res = new ArrayList<>();

        List<Producto> busquedaInicial =  database.query("SELECT * FROM producto ORDER BY precio DESC",new ProductMapper(),descripcion);

        for(Producto product : busquedaInicial){
            if(StringComparison.areSimilar(product.getDescripcion(), descripcion)){
                res.add(product);
            }
        }
        return res;

    }


    public List<Producto> getProductsFromOneVendor(String vendorName){
        return database.query("SELECT id_vendedor,descripcion,id,precio,material FROM producto WHERE id = ANY(SELECT v.id FROM vendedor v WHERE v.vendedor_nombre = ?) ",new ProductMapper(),vendorName);
    }

    public List<Producto> getAllProducts(){
        return database.query("SELECT id_vendedor,descripcion,id,precio,material FROM producto",new ProductMapper());
    }


    public void insertProduct(String nombre, int id_vendedor, double precio){
        database.update("INSERT INTO consumidor (nombre, id_vendedor, precio,precio) VALUES (?,?,?)",nombre,id_vendedor,precio);
    }
    


}
