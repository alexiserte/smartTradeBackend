package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.VendedorMapper;
import com.smartTrade.backend.Models.Vendedor;

import com.smartTrade.backend.Utils.DateMethods;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;


@Repository
public class VendedorDAO implements DAOInterface<Vendedor>{
    
    private JdbcTemplate database;

    public VendedorDAO(JdbcTemplate database) {
        this.database = database;
    }

    @Transactional
    public void create(Map<String,?> args) {
        String nickname = (String) args.get("nickname");
        String password = (String) args.get("password");
        String correo = (String) args.get("correo");
        String direccion = (String) args.get("direccion");
        String pais = (String) args.get("pais");
        String ciudad = (String) args.get("ciudad");

        Date fechaSQL = DateMethods.getTodayDate();

        database.update("INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro, pais, ciudad) VALUES (?, ?, ?, ?, ?, ?, ?);", nickname, correo, password, direccion, fechaSQL, pais, ciudad);
        database.update("INSERT INTO Vendedor(id_usuario) SELECT id FROM Usuario WHERE nickname = ?;", nickname);

    }

    public Vendedor readOne(Map<String,?> args) {
        String identifier = (String) args.get("identifier");
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro, u.pais, u.ciudad FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)", new VendedorMapper(), identifier, identifier);
    }

    public List<Vendedor> readAll() {
        return database.query("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro, u.pais, u.ciudad FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id", new VendedorMapper());
    }

    @SuppressWarnings("unchecked")
    public void update(Map<String,?> args) {
        String nickname = (String) args.get("nickname");
        Map<String, Object> atributos = (Map<String, Object>) args.get("atributos");
        List<String> keys = new ArrayList<>(atributos.keySet());
        Vendedor compradorObject = readOne(Map.of("identifier",nickname));
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
            }else if (key.equals("pais")) {
                if (atributos.get(key) == compradorObject.getCountry()) {
                    iterator.remove();
                }
            }else if (key.equals("ciudad")) {
                if (atributos.get(key) == compradorObject.getCity()) {
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

    public void delete(Map<String,?> args) {
        String nickname = (String) args.get("nickname");
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

    public int getVendorID(String nickname) {
        return database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, nickname);
    }

    public Vendedor getVendedorWithID(int id_vendedor) {
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro, u.pais,u.ciudad FROM Usuario u, Vendedor v WHERE v.id_usuario = u.id AND u.id = ?", new VendedorMapper(), id_vendedor);
    }


    
}
