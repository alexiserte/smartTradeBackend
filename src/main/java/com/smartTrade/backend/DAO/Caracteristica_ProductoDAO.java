package com.smartTrade.backend.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartTrade.backend.Mappers.CaracteristicaMapper;
import com.smartTrade.backend.Models.Caracteristica;

@Repository
public class Caracteristica_ProductoDAO implements DAOInterface<Caracteristica> {
    private JdbcTemplate database;

    public Caracteristica_ProductoDAO(JdbcTemplate database) {
        this.database = database;
    }

    @Autowired
    CaracteristicaDAO caracteristicaDAO;

    public void create(Map<String, ?> args) {
        String nombre = (String) args.get("nombre");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        String valor = (String) args.get("valor");
        String characteristicName = (String) args.get("characteristicName");
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?;", Integer.class, characteristicName);
        database.update("INSERT INTO Caracteristica(nombre, id_producto, valor, id_categoria) VALUES (?, ?, ?, ?);", nombre, id_producto, valor, id_categoria);
    }

    public Caracteristica readOne(Map<String, ?> args) {
        String nombre = (String) args.get("nombre");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        return database.queryForObject("SELECT nombre, valor, id_categoria, id_producto FROM Caracteristica WHERE nombre = ? AND id_producto = ?", new CaracteristicaMapper(), nombre, id_producto);
    }

    public List<Caracteristica> readAll() {
        return database.query("SELECT nombre, valor, id_categoria, id_producto FROM Caracteristica", new CaracteristicaMapper());
    }

    @SuppressWarnings("unchecked")
    public void update(Map<String, ?> args) {
        String nombre = (String) args.get("nombre");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        HashMap<String, Object> atributos = (HashMap<String, Object>) args.get("atributos");
        List<String> keys = new ArrayList<>(atributos.keySet());
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        for (String key : keys) {
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_producto = ?;", (Integer) valor, nombre, id_producto);
            } else if (valor instanceof String) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_producto = ?;", (String) valor, nombre, id_producto);
            } else if (valor instanceof Double) {
                database.update("UPDATE Caracteristica SET " + key + " = ? WHERE nombre = ? AND id_producto = ?;", (Double) valor, nombre, id_producto);
            }
        }
    }

    public void delete(Map<String, ?> args) {
        String nombre = (String) args.get("nombre");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = (SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?));", Integer.class, productName, vendorName);
        database.update("DELETE FROM Caracteristica WHERE nombre = ? AND id_producto = ?;", nombre, id_producto);
    }

    public HashMap<String, String> getSmartTag(String productName) {
        List<String> listaDeCaracteristica = new ArrayList<>();

        int id_categoria = database.queryForObject("SELECT id_categoria FROM Producto WHERE nombre = ?", Integer.class, productName);
        String categoria = database.queryForObject("SELECT nombre FROM Categoria WHERE id = ?;", String.class, id_categoria);

        listaDeCaracteristica = caracteristicaDAO.getCaracteristicasFromOneCategoria(categoria);

        HashMap<String, String> default_map = new HashMap<>();

        for (String s : listaDeCaracteristica) {
            default_map.put(s, "Esta caracteristica no esta disponible para este producto.");
        }

        HashMap<String, String> res = new HashMap<>();
        try {
            int id_producto = database.queryForObject(
                    "SELECT id FROM Producto WHERE nombre = ?",
                    Integer.class, productName);

            List<Caracteristica> queryResult = database.query(
                    "SELECT id_caracteristica, valor, id_categoria, id_producto FROM Caracteristica WHERE id_producto = ?",
                    new CaracteristicaMapper(), id_producto);

            List<Object> valores = new ArrayList<>();
            List<String> nombres = new ArrayList<>();
            for (Caracteristica c : queryResult) {
                valores.add(c.getValor());
                String nombre = database.queryForObject("SELECT nombre FROM Caracteristicas_Categoria WHERE id = ?",
                        String.class, c.getId_caracteristica());
                nombres.add(nombre);
            }

            for (int i = 0; i < nombres.size(); i++) {
                res.put(nombres.get(i), valores.get(i).toString());
            }
        } catch (EmptyResultDataAccessException e) {
            return default_map;
        }

        res.remove("product");
        return res;
    }
}
