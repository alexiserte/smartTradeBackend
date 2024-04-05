package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.CompradorMapper;
import com.smartTrade.backend.models.Comprador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;

@Repository
public class CompradorDAO{
    
    private JdbcTemplate database;

    public CompradorDAO(JdbcTemplate database) {
        this.database = database;
    }


    public Comprador getCompradorByIdentifier(String identifier){
        return database.queryForObject("SELECT nickname,user_password, correo, direccion,puntos_responsabilidad, fecha_registro FROM comprador WHERE nickname = ? OR correo = ?",new CompradorMapper(),identifier, identifier);
    }

    public List<Comprador> getCompradorByDireccion(String direccion){
        return database.query("SELECT nickname,user_password, correo, direccion,puntos_responsabilidad, fecha_registro FROM comprador WHERE direccion = ?",new CompradorMapper(),direccion);
    }


    public Comprador getUserPassword(String id){
        return database.queryForObject("SELECT user_password FROM Comprador WHERE id_usuario = ?",new CompradorMapper(),id);
    }

    public Comprador getCompradorByNicknameORMailAndPassword(String identifier, String password){
        return database.queryForObject("SELECT nickname,user_password, correo ,direccion,puntos_responsabilidad, fecha_registro FROM comprador WHERE (nickname = ? OR correo = ?) AND user_password = ?",new CompradorMapper(),identifier,identifier, password);
    }

    public void insertComprador(String nickname, String password,String correo){
        java.util.Date fechaActual = new java.util.Date();
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
        database.update("INSERT INTO comprador (nickname, user_password ,puntos_responsabilidad,correo,fecha_registro) VALUES (?,?,0,?)",nickname, password,correo,fechaSQL);
    }
   
    public List<Comprador> getAllCompradores(){
        return database.query("SELECT nickname,user_password, correo ,direccion,puntos_responsabilidad, fecha_registro FROM comprador",new CompradorMapper());
    }

    public boolean existsComprador(String nickname){
        try{
            database.queryForObject("SELECT nickname,user_password, correo ,direccion,puntos_responsabilidad FROM comprador WHERE nickname = ?",new CompradorMapper(),nickname);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void updateCompradorDireccion(String nickname, String direccion){
        database.update("UPDATE comprador SET direccion = ? WHERE nickname = ? ",direccion, nickname);
    }

    public void changeCompradorPassword(String nickname, String password){
        database.update("UPDATE comprador SET user_password = ? WHERE nickname = ?",password, nickname);
    }

    public void deleteComprador(int id){
        database.update("DELETE FROM comprador WHERE id_comprador = ?",id);
    }
    



}
