package com.smartTrade.backend.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Usuario;

@Repository
public class UsuarioDAO implements DAOInterface<Usuario>{
    private JdbcTemplate database;

    public UsuarioDAO (JdbcTemplate database) {
        this.database = database;
    }

    public User_Types whatTypeIs(String identifier){
        int id_usuario = database.queryForObject("SELECT id FROM Usuario WHERE (nickname = ? OR correo = ?);", Integer.class, identifier, identifier);
        
        boolean existsAdmin = database.queryForObject("SELECT COUNT(*) FROM Administrador WHERE id_usuario = ?", Integer.class, id_usuario) > 0;
        boolean existsVendor = database.queryForObject("SELECT COUNT(*) FROM Vendedor WHERE id_usuario = ?", Integer.class, id_usuario) > 0;
        boolean existsClient = database.queryForObject("SELECT COUNT(*) FROM Comprador WHERE id_usuario = ?", Integer.class, id_usuario) > 0;

        if (existsAdmin) {
            return User_Types.ADMINISTRADOR;
        } else if (existsVendor) {
            return User_Types.VENDEDOR;
        } else if (existsClient) {
            return User_Types.COMPRADOR;
        } else {
             throw new IllegalArgumentException("Los parámetros ingresados no corresponden a un usuario existente en la base de datos.");
        }
    }

    public void create(Object ...args){}
    public void update(Object ...args){}
    public void delete(Object ...args){}
    public Usuario readOne(Object ...args){return null;}
    public List<Usuario> readAll(){return null;}
}
