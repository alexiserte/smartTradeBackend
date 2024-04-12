package com.smartTrade.backend.daos;

import com.smartTrade.backend.mappers.VendedorMapper;
import com.smartTrade.backend.models.Vendedor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;


@Repository
public class VendedorDAO{
    
    private JdbcTemplate database;

    public VendedorDAO(JdbcTemplate database) {
        this.database = database;
    }

    @Transactional
    public void create(String nickname, String password, String correo, String direccion){
        
        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

        database.update("INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?);", nickname, correo, password, direccion, fechaSQL);
        database.update("INSERT INTO Vendedor(id_usuario) SELECT id FROM Usuario WHERE nickname = ?;", nickname);

    }

    public Vendedor readOne(String identifier) {
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)", new VendedorMapper(), identifier, identifier);
    }

    public List<Vendedor> readAll() {
        return database.query("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id", new VendedorMapper());
    }

    public void update(String nickname, Map<String, ?> atributos) {
        List<String> keys = new ArrayList<>(atributos.keySet());
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

    public void delete(String nickname) {
        database.update("DELETE FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Usuario WHERE nickname = ?;", nickname);

    }

    public int productosVendidosPorUnVendedor(String identifier) {
        return database.queryForObject("SELECT SUM(cantidad) FROM Detalle_Pedido WHERE id_producto = ANY(SELECT id FROM Producto WHERE id_vendedor = ANY(SELECT id_usuario FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ? OR correo = ?)))",Integer.class, identifier, identifier);
    }
}
