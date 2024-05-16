package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.CompradorMapper;
import com.smartTrade.backend.Models.Comprador;

import com.smartTrade.backend.Utils.DateMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

@Repository
public class CompradorDAO implements DAOInterface<Comprador>{

    private JdbcTemplate database;

    public CompradorDAO(JdbcTemplate database) {
        this.database = database;
    }

    
    @Autowired
    Carrito_CompraDAO carritoCompraDAO;

    @Autowired
    GuardarMasTardeDAO guardarMasTardeDAO;

    @Autowired
    ListaDeDeseosDAO listaDeDeseosDAO;

    @Transactional
    public void create(Map<String,?> args) {
        String nickname = (String) args.get("nickname");
        String password = (String) args.get("password");
        String correo = (String) args.get("correo");
        String direccion = (String) args.get("direccion");

        Date fechaSQL = DateMethods.getTodayDate();

        database.update("INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?);", nickname, correo, password, direccion, fechaSQL);
        database.update("INSERT INTO Comprador(id_usuario, puntos_responsabilidad) SELECT id, 0 FROM Usuario WHERE nickname = ?;", nickname);
        carritoCompraDAO.create(Map.of("compradorName",nickname));
        guardarMasTardeDAO.create(Map.of("compradorName",nickname));
        listaDeDeseosDAO.create(Map.of("compradorName",nickname));
    }

    public Comprador readOne(Map<String,?> args) {
        String identifier = (String) args.get("identifier");
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro, c.puntos_responsabilidad FROM Usuario u, Comprador c WHERE c.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)", new CompradorMapper(), identifier, identifier);
    }

    public List<Comprador> readAll() {
        return database.query("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro, c.puntos_responsabilidad FROM Usuario u, Comprador c WHERE c.id_usuario = u.id", new CompradorMapper());

    }

    @SuppressWarnings("unchecked")
    public void update(Map<String,?> args) {
    String nickname = (String) args.get("nickname");
    Map<String, Object> atributos = (Map<String, Object>) args.get("atributos");
        List<String> keys = new ArrayList<>(atributos.keySet());
    Comprador compradorObject = readOne(Map.of("identifier",nickname));
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
        } else if (key.equals("puntos_responsabilidad")) {
            if ((atributos.get(key)).equals(compradorObject.getpuntosResponsabilidad())) {
                iterator.remove();
            }
        }

    }

    if (keys.isEmpty()) {
        return;
    }
        for (String key : keys) {
            if(key.equals("puntos_responsabilidad")){
                database.update("UPDATE Comprador SET puntos_responsabilidad = ? WHERE id_usuario = (SELECT id FROM Usuario WHERE nickname = ?);", (Integer) atributos.get(key), nickname);
            }
            else{
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
    }

    public void delete(Map<String,?> args) {
        String nickname = (String) args.get("nickname");

        database.update("DELETE FROM Carrito_Compra WHERE id_comprador IN(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Guardar_Mas_Tarde WHERE id_comprador IN(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Lista_De_Deseos WHERE id_comprador IN(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Comprador WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Usuario WHERE nickname = ?;", nickname);

    }

    public int productosCompradosPorUnUsuario(String identifier) {
        int numeroDeProductos = database.queryForObject(
                "SELECT COUNT(*) FROM Detalle_Pedido WHERE id_pedido = ANY(SELECT id FROM Pedido WHERE id_comprador = ANY(SELECT id_usuario FROM Comprador WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ? OR correo = ?)))",
                Integer.class, identifier, identifier);
        if (numeroDeProductos == 0) {
            return 0;
        } else {
            return database.queryForObject(
                    "SELECT SUM(cantidad) FROM Detalle_Pedido WHERE id_pedido = ANY(SELECT id FROM Pedido WHERE id_comprador = ANY(SELECT id_usuario FROM Comprador WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ? OR correo = ?)))",
                    Integer.class, identifier, identifier);
        }
    }

    public Comprador getCompradorWithID(int id_usuario){
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro, c.puntos_responsabilidad FROM Usuario u, Comprador c WHERE c.id_usuario = u.id AND u.id = ?", new CompradorMapper(), id_usuario);
    }

}
