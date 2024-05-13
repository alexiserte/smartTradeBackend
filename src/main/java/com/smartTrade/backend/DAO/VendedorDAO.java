package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.VendedorMapper;
import com.smartTrade.backend.Models.Vendedor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;


@Repository
public class VendedorDAO implements DAOInterface<Vendedor>{
    
    private JdbcTemplate database;

    public VendedorDAO(JdbcTemplate database) {
        this.database = database;
    }

    @Transactional
    public void create(Object ...args) {
        String nickname = (String) args[0];
        String password = (String) args[1];
        String correo = (String) args[2];
        String direccion = (String) args[3];
        
        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

        database.update("INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?);", nickname, correo, password, direccion, fechaSQL);
        database.update("INSERT INTO Vendedor(id_usuario) SELECT id FROM Usuario WHERE nickname = ?;", nickname);

    }

    public Vendedor readOne(Object ...args) {
        String identifier = (String) args[0];
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)", new VendedorMapper(), identifier, identifier);
    }

    public List<Vendedor> readAll() {
        return database.query("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id", new VendedorMapper());
    }

    @SuppressWarnings("unchecked")
    public void update(Object ...args) {
        String nickname = (String) args[0];
        Map<String, Object> atributos = (Map<String, Object>) args[1];
        List<String> keys = new ArrayList<>(atributos.keySet());
        Vendedor compradorObject = readOne(nickname);
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            if (key.equals("nickname")) {
                if (atributos.get(key).equals(compradorObject.getNickname())) {
                    iterator.remove();
                }
            } else if (key.equals("password")) {
                if (atributos.get(key) == (compradorObject.getPassword())) {
                    iterator.remove();
                }
            } else if (key.equals("correo")) {
                if (atributos.get(key) == compradorObject.getCorreo()) {
                    iterator.remove();
                }
            } else if (key.equals("direccion")) {
                if (atributos.get(key) == compradorObject.getDireccion()) {
                    iterator.remove();
                }
            }

        }

        if (keys.isEmpty()) {
            return;
        }
        for (String key : keys) {

            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Usuario SET " + key + " = ? WHERE nickname = ?;", (Integer) valor, nickname);
            } else if (valor instanceof String) {
                database.update("UPDATE Usuario SET " + key + " = ? WHERE nickname = ?;", (String) valor, nickname);
            } else if (valor instanceof Double) {
                database.update("UPDATE Usuario SET " + key + " = ? WHERE nickname = ?;", (Double) valor, nickname);
            }

        }
    }

    public void delete(Object ...args) {
        String nickname = (String) args[0];
        database.update("DELETE FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Usuario WHERE nickname = ?;", nickname);

    }

    public int productosVendidosPorUnVendedor(String identifier) {
        int numResults = database.queryForObject(
                "SELECT COUNT(*) FROM Detalle_Pedido WHERE id_producto = ANY(SELECT id FROM Producto WHERE id_vendedor = ANY(SELECT id_usuario FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ? OR correo = ?)))",
                Integer.class, identifier, identifier);
        if (numResults == 0) {
            return 0;
        } else {
            return database.queryForObject(
                    "SELECT SUM(cantidad) FROM Detalle_Pedido WHERE id_producto = ANY(SELECT id FROM Producto WHERE id_vendedor = ANY(SELECT id_usuario FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ? OR correo = ?)))",
                    Integer.class, identifier, identifier);
        }
    }

    public String getVendorName(int id_vendedor) {
        return database.queryForObject("SELECT nickname FROM Usuario WHERE id = ?", String.class, id_vendedor);
    }

    public Vendedor getVendedorWithID(int id_vendedor) {
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id AND u.id = ?", new VendedorMapper(), id_vendedor);
    }


    
}
