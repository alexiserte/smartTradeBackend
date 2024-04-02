package com.smartTrade.backend.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import com.smartTrade.backend.mappers.CaracteristicaMapper;
import java.util.HashMap;
import java.util.List;
import com.smartTrade.backend.models.Caracteristica;

public class CaracteristicaDAO {
    private JdbcTemplate database;

    public CaracteristicaDAO(JdbcTemplate database) {
        this.database = database;
    }

    public HashMap<String,String> getCaracteristicas(int id_producto) {
        HashMap<String,String> res  = new HashMap<>();
        List<Caracteristica> queryResult = database.query("SELECT nombre, valor FROM caracteristica WHERE id_producto = ?", new CaracteristicaMapper(), id_producto);
        
        for(Caracteristica c : queryResult) {
            res.put(c.getNombre(), c.getValor());
        }
        return res;
    }

}
