package com.smartTrade.backend.DAO;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuardarMasTardeDAO implements DAOInterface<Object>{
    
    private JdbcTemplate database;

    public GuardarMasTardeDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(Object ...args) {
        String compradorName = (String) args[0];
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Guardar_Mas_Tarde(id_comprador) VALUES (?)",id_comprador);
    }

    public void insertarProducto(String userNickname,String productName,String vendorName){
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_producto = database.queryForObject("SELECT id_producto FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_guardar_mas_tarde = database.queryForObject("SELECT id FROM Guardar_Mas_Tarde WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("INSERT INTO Productos_Guardar_Mas_Tarde(id_producto,id_guardar_mas_tarde,id_vendedor) VALUES (?,?,?)",id_producto,id_guardar_mas_tarde,id_vendedor);
    }

    public void deleteProducto(String userNickname,String productName,String vendorName){
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_producto = database.queryForObject("SELECT id_producto FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_guardar_mas_tarde = database.queryForObject("SELECT id FROM Guardar_Mas_Tarde WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("DELETE FROM Productos_Guardar_Mas_Tarde WHERE id_producto = ? AND id_guardar_mas_tarde = ? AND id_vendedor = ?",id_producto,id_guardar_mas_tarde,id_vendedor);
    }

    public void delete(Object ...args) {;}
    public void update(Object ...args) {;}
    public Object readOne(Object ...args) {return null;}
    public List<Object> readAll() {return null;}
    

}
