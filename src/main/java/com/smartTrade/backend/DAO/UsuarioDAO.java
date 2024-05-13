package com.smartTrade.backend.DAO;

import java.util.List;

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


    public void create(Object ...args){}
    public void update(Object ...args){}
    public void delete(Object ...args){}
    public Usuario readOne(Object ...args){return null;}
    public List<Usuario> readAll(){return null;}

    public int getID(String identifier){
        return database.queryForObject("SELECT id FROM Usuario WHERE (nickname = ? OR correo = ?);", Integer.class, identifier, identifier);
    }
}
