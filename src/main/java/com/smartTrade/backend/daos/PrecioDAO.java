package com.smartTrade.backend.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public class PrecioDAO {

    private final JdbcTemplate database;

    public PrecioDAO(JdbcTemplate database) {
        this.database = database;
    }

    public HashMap<String, Object> getStats(String productName) {
        HashMap<String, Object> stats = new HashMap<>();
        int id_product = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?",Integer.class,productName);
        int numerodecambios = database.queryForObject("SELECT COUNT(*) FROM Historico_Precios WHERE id_producto = ?",Integer.class,id_product);
        double preciominimo = database.queryForObject("SELECT MIN(precio) FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        double preciomaximo = database.queryForObject("SELECT MAX(precio) FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        double preciomedio = database.queryForObject("SELECT AVG(precio) FROM Historico_Precios WHERE id_producto = ?",Double.class,id_product);
        
        List<String> vendedores = database.queryForList("SELECT nickname FROM Usuario WHERE id IN(SELECT id_vendedor FROM Vendedores_Producto WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ?))", String.class, productName);
        HashMap<String,Double> resultado = new HashMap<>();
        
        for (int j = 0; j < vendedores.size(); j++) {
            List<Double> preciosFromOneProduct = database.queryForList("SELECT precio FROM Historico_Precios WHERE id_producto = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ?)",Double.class, id_product, vendedores.get(j));
            HashMap<String, Object> preciosVendedor = new HashMap<>();
            preciosVendedor.put("Vendedor", vendedores.get(j));
            for (int i = 0; i < preciosFromOneProduct.size(); i++) {
                preciosVendedor.put("Precio " + (i + 1), preciosFromOneProduct.get(i));
            }
            preciosVendedor.put("Precio mínimo", database.queryForObject("SELECT MIN(precio) FROM Historico_Precios WHERE id_producto = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ?)",Double.class, id_product, vendedores.get(j)));
            preciosVendedor.put("Precio máximo", database.queryForObject("SELECT MAX(precio) FROM Historico_Precios WHERE id_producto = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ?)",Double.class, id_product, vendedores.get(j)));
            preciosVendedor.put("Precio promedio", database.queryForObject("SELECT AVG(precio) FROM Historico_Precios WHERE id_producto = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ?)",Double.class, id_product, vendedores.get(j)));
            
            stats.put("Vendedor " + (j + 1), preciosVendedor);
            //AQUI DEBE IR EL MÉTODO QUE RETORNA DATOS CURIOSOS SOBRE EL PRECIO DE UN PRODUCTO
            return stats;
        }



        return null;
    }
}
