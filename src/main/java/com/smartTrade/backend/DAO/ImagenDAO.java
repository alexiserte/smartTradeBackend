package com.smartTrade.backend.DAO;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Factory.ConverterFactory;
import com.smartTrade.backend.Utils.*;


@Repository
public class ImagenDAO implements DAOInterface<String>{
    
    private JdbcTemplate database;

    public ImagenDAO(JdbcTemplate database) {
        this.database = database;
    }

    ConverterFactory factory = new ConverterFactory();
    PNGConverter converter = (PNGConverter) factory.createConversor("PNG");
    

    public void create(Object ...args) {
        String image = (String) args[0];
        String imagenResized = converter.procesar(image);
        database.update("INSERT INTO Imagen(imagen) VALUES (?)",imagenResized);
    }

    public void update(Object ...args) {
        int id_imagen = (int) args[0];
        String image = (String) args[1];
        String imagenResized = converter.procesar(image);
        database.update("UPDATE Imagen SET imagen = ? WHERE id = ?",imagenResized,id_imagen);
    }

    public void delete(Object ...args) {
        int id_imagen = (int) args[0];
        database.update("DELETE FROM Imagen WHERE id = ?",id_imagen);
    }

    public String readOne(Object ...args) {
        int id_imagen = (int) args[0];
        return database.queryForObject("SELECT imagen FROM Imagen WHERE id = ?", String.class, id_imagen);
    }


    public List<String> readAll() {
        return database.queryForList("SELECT imagen FROM Imagen ORDER BY id", String.class);
    }

    

}
