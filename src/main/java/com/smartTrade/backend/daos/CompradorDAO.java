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
  
    public Comprador getCompradorByID(int id){
        return database.queryForObject("SELECT * FROM consumidor WHERE id_consumidor = ?",new CompradorMapper(),id);
    }

    public Comprador getCompradorByIDAndNombre(int id, String nombre){
        return database.queryForObject("SELECT * FROM consumidor WHERE nickname = ? AND id_consumidor = ?",new CompradorMapper(),nombre, id);
    }

    public Comprador getCompradorByNombre(String nombre){
        return database.queryForObject("SELECT * FROM consumidor WHERE nickname = ?",new CompradorMapper(),nombre);
    }

    public List<Comprador> getCompradorByDireccion(String direccion){
        return database.query("SELECT * FROM consumidor WHERE direccion = ?",new CompradorMapper(),direccion);
    }

    public List<Comprador> getCompradorByPuntosResponsabilidad(int puntos){
        return database.query("SELECT * FROM consumidor WHERE puntos_responsabilidad = ?",new CompradorMapper(),puntos);
    }

    public List<Comprador> getCompradorByPuntosResponsabilidadAndDireccion(int puntos, String direccion){
        return database.query("SELECT * FROM consumidor WHERE puntos_responsabilidad = ? AND direccion = ?",new CompradorMapper(),puntos, direccion);
    }

    public List<Comprador> getCompradorByPuntosResponsabilidadAndNombre(int puntos, String nombre){
        return database.query("SELECT * FROM consumidor WHERE puntos_responsabilidad = ? AND nickname = ?",new CompradorMapper(),puntos, nombre);
    }

    public List<Comprador> getCompradorByDireccionAndNombre(String direccion, String nombre){
        return database.query("SELECT * FROM consumidor WHERE direccion = ? AND nickname = ?",new CompradorMapper(),direccion, nombre);
    }

    public List<Comprador> getCompradorByDireccionAndNombreAndPuntosResponsabilidad(String direccion, String nombre, int puntos){
        return database.query("SELECT * FROM consumidor WHERE direccion = ? AND nickname = ? AND puntos_responsabilidad = ?",new CompradorMapper(),direccion, nombre, puntos);
    }

    public List<Comprador> getCompradorByDireccionAndPuntosResponsabilidad(String direccion, int puntos){
        return database.query("SELECT * FROM consumidor WHERE direccion = ? AND puntos_responsabilidad = ?",new CompradorMapper(),direccion, puntos);
    }

    public List<Comprador> getCompradorByNombreAndPuntosResponsabilidad(String nombre, int puntos){
        return database.query("SELECT * FROM consumidor WHERE nickname = ? AND puntos_responsabilidad = ?",new CompradorMapper(),nombre, puntos);
    }

    public List<Comprador> getCompradorByDireccionAndNombreAndPuntosResponsabilidadAndID(String direccion, String nombre, int puntos, int id){
        return database.query("SELECT * FROM consumidor WHERE direccion = ? AND nickname = ? AND puntos_responsabilidad = ? AND id_consumidor = ?",new CompradorMapper(),direccion, nombre, puntos, id);
    }

    public Comprador getUserPassword(String id){
        return database.queryForObject("SELECT user_password FROM consumidor WHERE id_consumidor = ?",new CompradorMapper(),id);
    }

    public Comprador getCompradorByNicknameAndPassword(String nickname, String password){
        return database.queryForObject("SELECT * FROM consumidor WHERE nickname = ? AND user_password = ?",new CompradorMapper(),nickname, password);
    }

    public void insertCompradorOnlyNicknameAndPassword(int id, String nickname, String password){
        database.update("INSERT INTO consumidor (id_consumidor, nickname, user_password) VALUES (?,?,?)",id, nickname, password);
    }
   
    public List<Comprador> getAllCompradores(){
        return database.query("SELECT * FROM consumidor",new CompradorMapper());
    }

    public boolean existsComprador(String nickname){
        try{
            database.queryForObject("SELECT * FROM consumidor WHERE nickname = ?",new CompradorMapper(),nickname);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void updateCompradorDireccion(int id, String direccion){
        database.update("UPDATE consumidor SET direccion = ? WHERE id_consumidor = ?",direccion, id);
    }

    public void changeCompradorPassword(String nickname, String password){
        database.update("UPDATE consumidor SET user_password = ? WHERE nickname = ?",password, nickname);
    }

    public void deleteComprador(int id){
        database.update("DELETE FROM consumidor WHERE id_consumidor = ?",id);
    }



}
