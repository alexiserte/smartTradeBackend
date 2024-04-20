package com.smartTrade.backend.daos;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.utils.PNGConverter;

@Repository
public class ImagenDAO{
    
    private JdbcTemplate database;

    public ImagenDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(String image) {
        String imagenResized = PNGConverter.convertAndResizeImageToBase64(image);
        database.update("INSERT INTO Imagen(imagen) VALUES (?)",imagenResized);
    }

    public void update(int id_imagen, String image) {
        String imagenResized = PNGConverter.convertAndResizeImageToBase64(image);
        database.update("UPDATE Imagen SET imagen = ? WHERE id_imagen = ?",imagenResized,id_imagen);
    }

    public void delete(int id_imagen) {
        database.update("DELETE FROM Imagen WHERE id_imagen = ?",id_imagen);
    }

    public String read(int id_imagen) {
        return database.queryForObject("SELECT imagen FROM Imagen WHERE id_imagen = ?", String.class, id_imagen);
    }


    public List<String> readAll() {
        return database.queryForList("SELECT imagen FROM Imagen ORDER BY id", String.class);
    }

    

}
