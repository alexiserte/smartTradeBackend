package com.smartTrade.backend.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public class PrecioDAO {
    
    private final JdbcTemplate database;

    public PrecioDAO(JdbcTemplate database) {
        this.database = database;
    }

    public HashMap<String, ?> getStats(String productName,String vendorName) {
        int id_product = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ?)",Integer.class,productName,vendorName);
        int numerodecambios = database.queryForObject("SELECT COUNT(*) FROM Historico_Precios WHERE id_producto = ?",Integer.class,id_product);
        double preciominimo = database.queryForObject("SELECT MIN(precio) FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        double preciomaximo = database.queryForObject("SELECT MAX(precio) FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        double preciomedio = database.queryForObject("SELECT AVG(precio) FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        List<Double> historicoPrecios = database.queryForList("SELECT precio FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        HashMap<String,Double> precios = new HashMap<>();
        for(int i = 0; i < historicoPrecios.size(); i++){
            precios.put("Precio " + i, historicoPrecios.get(i));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("Producto", productName);
        res.put("Número de cambios de precio", numerodecambios);
        res.put("Precio mínimo", preciominimo);
        res.put("Precio máximo", preciomaximo);
        res.put("Precio medio", preciomedio);
        res.put("Histórico de precios", precios);
        return res;
    }
}
