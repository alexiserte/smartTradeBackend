package com.smartTrade.backend.daos;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.*;

@Repository
public class Carrito_CompraDAO{
    
    private JdbcTemplate database;

    public Carrito_CompraDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(String compradorName) {
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Carrito_Compra(id_comprador) VALUES (?)",id_comprador);
    }

    public List<Producto> getCarritoFromUser(String nickname){
        return database.query("SELECT nombre, id_categoria, descripcion, id_imagen,fecha_añadido,validado,huella_ecologica FROM Producto WHERE id IN(SELECT id_producto FROM Productos_Carrito WHERE id_carrito IN(SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)))",new ProductMapper(),nickname);
    }
    

}
