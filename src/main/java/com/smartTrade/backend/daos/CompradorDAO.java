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
        return database.queryForObject("SELECT id_consumidor,nickname,user_password,direccion,puntos_responsabilidad FROM consumidor WHERE nickname = ?",new CompradorMapper(),nombre);
    }

    public List<Comprador> getCompradorByDireccion(String direccion){
        return database.query("SELECT id_consumidor,nickname,user_password,direccion,puntos_responsabilidad FROM consumidor WHERE direccion = ?",new CompradorMapper(),direccion);
    }


    public Comprador getUserPassword(String id){
        return database.queryForObject("SELECT user_password FROM consumidor WHERE id_consumidor = ?",new CompradorMapper(),id);
    }

    public Comprador getCompradorByNicknameAndPassword(String nickname, String password){
        return database.queryForObject("SELECT id_consumidor,nickname,user_password,direccion,puntos_responsabilidad FROM consumidor WHERE nickname = ? AND user_password = ?",new CompradorMapper(),nickname, password);
    }

    public void insertCompradorOnlyNicknameAndPassword(String nickname, String password){
        database.update("INSERT INTO consumidor (nickname, user_password) VALUES (?,?)",nickname, password);
    }
   
    public List<Comprador> getAllCompradores(){
        return database.query("SELECT id_consumidor,nickname,user_password,direccion,puntos_responsabilidad FROM consumidor",new CompradorMapper());
    }

    public boolean existsComprador(String nickname){
        try{
            database.queryForObject("SELECT id_consumidor,nickname,user_password,direccion,puntos_responsabilidad FROM consumidor WHERE nickname = ?",new CompradorMapper(),nickname);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void updateCompradorDireccion(String nickname, String direccion){
        database.update("UPDATE consumidor SET direccion = ? WHERE nickname = ? ",direccion, nickname);
    }

    public void changeCompradorPassword(String nickname, String password){
        database.update("UPDATE consumidor SET user_password = ? WHERE nickname = ?",password, nickname);
    }

    public void deleteComprador(int id){
        database.update("DELETE FROM consumidor WHERE id_consumidor = ?",id);
    }

    



}
