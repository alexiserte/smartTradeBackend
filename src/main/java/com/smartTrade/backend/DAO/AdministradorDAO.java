package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.AdministradorMapper;
import com.smartTrade.backend.Models.Administrador;
import com.smartTrade.backend.Utils.DateMethods;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Repository
public class AdministradorDAO implements DAOInterface<Administrador>{

    private final JdbcTemplate database;

    public AdministradorDAO(JdbcTemplate database) {
        this.database = database;
    }

    @Transactional
    public void create(Map<String,?> args) {
        String nickname = (String)  args.get("nickname");
        String password = (String) args.get("password");
        String correo = (String) args.get("correo");
        String direccion = (String) args.get("direccion");
        Date fechaSQL = DateMethods.getTodayDate();

        database.update(
                "INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?);",
                nickname, correo, password, direccion, fechaSQL);
        database.update("INSERT INTO Administrador(id_usuario) SELECT id FROM Usuario WHERE nickname = ?;", nickname);

    }

    public Administrador readOne(Map<String,?> args) {
        String identifier = (String) args.get("identifier");
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro FROM Usuario u, Administrador a WHERE a.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)", new AdministradorMapper(), identifier, identifier);
    }

    public List<Administrador> readAll() {
        return database.query("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro FROM Usuario u, Administrador a WHERE a.id_usuario = u.id", new AdministradorMapper());
    }


    @SuppressWarnings("unchecked")
    public void update(Map<String,?> args) {
        String nickname = (String) args.get("nickname");
        Map<String, Object> atributos = (Map<String, Object>) args.get("atributos");
        List<String> keys = new ArrayList<>(atributos.keySet());
        Administrador compradorObject = readOne(Map.of("nickname",nickname));
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

    public void delete(Map<String,?> args) {
        String nickname = (String) args.get("nickname");
        database.update("DELETE FROM Administrador WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Usuario WHERE nickname = ?;", nickname);
    }


    public Administrador getAdministradorWithID(int id){
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password, u.direccion, u.fecha_registro FROM Usuario u, Administrador a WHERE a.id_usuario = u.id AND u.id = ?", new AdministradorMapper(), id);
    }

}
