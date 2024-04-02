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


    public Comprador getCompradorByNombre(String nombre){
        return database.queryForObject("SELECT nickname,user_password, correo, direccion,puntos_responsabilidad FROM comprador WHERE nickname = ?",new CompradorMapper(),nombre);
    }

    public List<Comprador> getCompradorByDireccion(String direccion){
        return database.query("SELECT nickname,user_password, correo, direccion,puntos_responsabilidad FROM comprador WHERE direccion = ?",new CompradorMapper(),direccion);
    }


    public Comprador getUserPassword(String id){
        return database.queryForObject("SELECT user_password FROM comprador WHERE id_comprador = ?",new CompradorMapper(),id);
    }

    public Comprador getCompradorByNicknameAndPassword(String nickname, String password){
        return database.queryForObject("SELECT nickname,user_password, correo ,direccion,puntos_responsabilidad FROM comprador WHERE nickname = ? AND user_password = ?",new CompradorMapper(),nickname, password);
    }

    public void insertComprador(String nickname, String password,String correo){
        database.update("INSERT INTO comprador (nickname, user_password ,puntos_responsabilidad,correo) VALUES (?,?,0,?)",nickname, password,correo);
    }
   
    public List<Comprador> getAllCompradores(){
        return database.query("SELECT nickname,user_password, correo ,direccion,puntos_responsabilidad FROM comprador",new CompradorMapper());
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
