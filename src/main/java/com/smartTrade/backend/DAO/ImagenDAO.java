package com.smartTrade.backend.DAO;

import java.util.List;
import java.util.Map;

import com.smartTrade.backend.Template.PNGConverter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Factory.ConverterFactory;


@Repository
public class ImagenDAO implements DAOInterface<String> {

    private JdbcTemplate database;

    public ImagenDAO(JdbcTemplate database) {
        this.database = database;
    }

    private static final ConverterFactory factory = new ConverterFactory();
    private final PNGConverter converter = (PNGConverter) factory.createConversor("PNG");


    public void create(Map<String, ?> args) {
        String image = (String) args.get("imagen");
        String imagenResized = converter.procesar(image);
        database.update("INSERT INTO Imagen(imagen) VALUE(?)", imagenResized);
    }

    public void update(Map<String, ?> args) {
        int id_imagen = (int) args.get("id_imagen");
        String image = (String) args.get("imagen");
        String imagenResized = converter.procesar(image);
        database.update("UPDATE Imagen SET imagen = ? WHERE id = ?", imagenResized, id_imagen);
    }

    public void delete(Map<String, ?> args) {
        int id_imagen = (int) args.get("id_imagen");
        database.update("DELETE FROM Imagen WHERE id = ?", id_imagen);
    }

    public String readOne(Map<String, ?> args) {
        int id_imagen = (int) args.get("id_imagen");
        return database.queryForObject("SELECT imagen FROM Imagen WHERE id = ?", String.class, id_imagen);
    }


    public List<String> readAll() {
        return database.queryForList("SELECT imagen FROM Imagen ORDER BY id", String.class);
    }

    public Integer getID(String image) {
        try {
            return database.queryForObject("SELECT id FROM Imagen WHERE imagen = ?", Integer.class, image);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return -1;
        }
    }


}
