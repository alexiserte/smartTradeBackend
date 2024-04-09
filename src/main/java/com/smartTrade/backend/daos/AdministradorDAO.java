package com.smartTrade.backend.daos;

import com.smartTrade.backend.mappers.AdministradorMapper;
import com.smartTrade.backend.mappers.CompradorMapper;
import com.smartTrade.backend.mappers.DatabaseMapper;
import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.Comprador;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class AdministradorDAO {

    private final JdbcTemplate database;

    public AdministradorDAO(JdbcTemplate database) {
        this.database = database;
    }

    /**
     * El método "crear" inserta un nuevo usuario en la base de datos con el apodo,
     * contraseña, correo
     * electrónico, dirección y fecha de registro proporcionados, y también asigna
     * al usuario como
     * administrador.
     * 
     * @param nickname  El parámetro "apodo" suele ser un identificador único o
     *                  nombre de usuario elegido
     *                  por el usuario para su cuenta. Se utiliza para distinguir un
     *                  usuario de otro en el sistema.
     * @param password  El parámetro "contraseña" en el método "crear" es una cadena
     *                  que representa la
     *                  contraseña del usuario que se está creando.
     * @param correo    El parámetro "correo" en el método representa la dirección
     *                  de correo electrónico del
     *                  usuario que se está creando. Es una variable de tipo cadena
     *                  que almacena la dirección de correo
     *                  electrónico proporcionada durante el proceso de creación del
     *                  usuario.
     * @param direccion El parámetro "direccion" en el método representa la
     *                  dirección del usuario. Es una
     *                  cadena que normalmente incluye información como dirección,
     *                  ciudad, estado y código postal.
     */
    @Transactional
    public void create(String nickname, String password, String correo, String direccion) {

        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

        database.update(
                "INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?);",
                nickname, correo, password, direccion, fechaSQL);
        database.update("INSERT INTO Administrador(id_usuario) SELECT id FROM Usuario WHERE nickname = ?;", nickname);

    }

    /**
     * La función `readOne` recupera un objeto `Administrador` de la base de datos
     * en función de un
     * identificador determinado (apodo o correo electrónico).
     * 
     * @param identifier El parámetro "identificador" se utiliza para buscar un
     *                   objeto "Administrador"
     *                   específico según el apodo o la dirección de correo
     *                   electrónico proporcionada. La consulta buscará un
     *                   registro coincidente en la base de datos donde el `apodo` o
     *                   `correo` (correo electrónico) coincida
     *                   con el valor del `identificador`.
     * @return Se devuelve un objeto `Administrador` desde el método `readOne`. El
     *         método consulta la base
     *         de datos en busca de campos específicos relacionados con un
     *         administrador según el identificador
     *         proporcionado (apodo o correo electrónico) y luego asigna los
     *         resultados a un objeto "Administrador"
     *         utilizando "AdministradorMapper".
     */
    public Administrador readOne(String identifier) {
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro FROM Usuario u, Administrador a WHERE a.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)", new AdministradorMapper(), identifier, identifier);
    }

    /**
     * La función lee todos los objetos Comprador de la base de datos ejecutando una
     * consulta SQL
     * específica.
     * 
     * @return Se devuelve una lista de objetos Comprador. Los datos se recuperan de
     *         la base de datos
     *         ejecutando la consulta SQL que selecciona columnas específicas de la
     *         tabla Usuario donde la
     *         identificación coincide con la identificación en la tabla
     *         Administrador. Luego, los resultados se
     *         asignan a objetos Comprador utilizando CompradorMapper.
     */
    public List<Comprador> readAll() {
        return database.query("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro FROM Usuario u, Administrador a WHERE a.id_usuario = u.id", new CompradorMapper());
    }

    /**
     * El método "actualizar" toma un apodo y un mapa de atributos, luego actualiza
     * los valores
     * correspondientes en la base de datos según el tipo de atributo.
     * 
     * @param nickname  El método "actualizar" que proporcionó toma un apodo y un
     *                  mapa de atributos como
     *                  parámetros. Itera sobre las claves en el mapa y actualiza el
     *                  atributo correspondiente en la base de
     *                  datos para el apodo dado.
     * @param atributos El parámetro `atributos` es un mapa que contiene pares
     *                  clave-valor donde la clave
     *                  es un String que representa el atributo a actualizar en la
     *                  base de datos y el valor es de tipo
     *                  Objeto. El método itera sobre las claves en el mapa,
     *                  recupera el valor correspondiente y, según el
     *                  tipo de
     */
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

    /**
     * La función "eliminar" elimina un administrador y un usuario de la base de
     * datos según el apodo
     * proporcionado.
     * 
     * @param nickname El parámetro `nickname` en el método `delete` se utiliza para
     *                 especificar el apodo
     *                 del usuario cuyos registros deben eliminarse de la base de
     *                 datos. El método primero borra el
     *                 registro de la tabla `Administrador` donde el `id_usuario`
     *                 coincide con el `id` del usuario con el
     *                 especificado
     */
    public void delete(String nickname) {
        database.update("DELETE FROM Administrador WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);",
                nickname);
        database.update("DELETE FROM Usuario WHERE nickname = ?;", nickname);
    }

    /**
     * La función getAllDatabases recupera todas las tablas y sus atributos de un
     * esquema de base de
     * datos específico en Java.
     * 
     * @return Este método devuelve una lista de cadenas, donde cada cadena
     *         representa información
     *         sobre una tabla de base de datos en 'FiveGuysDatabase'. Cada cadena
     *         incluye el nombre de la
     *         tabla y sus atributos.
     */
    public List<String> getAllDatabases() {
        List<String> result = new ArrayList<>();
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'FiveGuysDatabase' AND TABLE_TYPE = 'BASE TABLE'";
        List<String> tables = database.queryForList(sql, String.class);
        for (String table : tables) {
            String attributesSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table
                    + "'";
            List<String> attributes = database.queryForList(attributesSql, String.class);
            StringBuilder tableInfo = new StringBuilder();
            tableInfo.append("Table: ").append(table).append(", Attributes: ");
            for (String attribute : attributes) {
                tableInfo.append(attribute).append(" ");
            }
            result.add(tableInfo.toString());
        }
        return result;
    }

}
