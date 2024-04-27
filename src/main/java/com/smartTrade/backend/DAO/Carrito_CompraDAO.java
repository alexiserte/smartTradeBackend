package com.smartTrade.backend.DAO;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.*;

@Repository
public class Carrito_CompraDAO implements DAOInterface<Object>{
    
    private JdbcTemplate database;

    public Carrito_CompraDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(Object ...args) {
        String compradorName = (String) args[0];
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Carrito_Compra(id_comprador) VALUES (?)",id_comprador);
    }

    public List<Producto> getCarritoFromUser(String nickname){
        return database.query("SELECT nombre, id_categoria, descripcion, id_imagen,fecha_añadido,validado,huella_ecologica FROM Producto WHERE id IN(SELECT id_producto FROM Productos_Carrito WHERE id_carrito IN(SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)))",new ProductMapper(),nickname);
    }

    public void insertarProduct(String productName,String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("INSERT INTO Productos_Carrito(id_carrito,id_producto,id_vendedor) VALUES(?,?,?)",id_carrito,id_producto,id_vendedor);
    }

    public void deleteProduct(String productName, String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("DELETE FROM Productos_Carrito WHERE id_carrito = ? AND id_producto = ? AND id_vendedor = ?",id_carrito,id_producto, id_vendedor);
    }

    public void delete(Object ...args) {;}
    public void update(Object ...args) {;}
    public Object readOne(Object ...args) {return null;}
    public List<Object> readAll() {return null;}
    

}
