package com.smartTrade.backend.daos;

import com.smartTrade.backend.mappers.CompradorMapper;
import com.smartTrade.backend.mappers.DatabaseMapper;
import com.smartTrade.backend.models.Comprador;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
     * El método "create" inserta un nuevo comprador en la base de datos con tablas asociadas para los
     * detalles del comprador, el carrito de compras, los artículos guardados y la lista de deseos.
     * 
     * @param nickname El parámetro `nickname` es el nombre de usuario o alias que el usuario elige
     * para identificarse en la plataforma. Es un identificador único para la cuenta del usuario.
     * @param password El parámetro "contraseña" en el método "crear" representa la contraseña del
     * usuario que se crea en el sistema. Esta contraseña se utiliza normalmente con fines de
     * autenticación cuando el usuario inicia sesión en su cuenta. Es importante almacenar y manejar de
     * forma segura las contraseñas para garantizar la seguridad de las cuentas de usuario. Es
     * recomendado
     * @param correo El parámetro "correo" en el método representa la dirección de correo electrónico
     * del usuario que se está creando. Se utiliza para almacenar la información de correo electrónico
     * en la base de datos para el nuevo usuario.
     * @param direccion El parámetro "direccion" en el método representa la dirección del usuario. Es
     * la ubicación física donde reside el usuario o donde desea que le entreguen sus compras.
     */
    public void create(String nickname, String password, String correo, String direccion){
        
        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

        database.update(
                        "INSERT Usuario(nickname,correo,user_password,direccion,fecha_registro)" + //
                        "VALUES(?,?,?,?,?);" + //
                        "INSERT Administrador(id_usuario,puntos_responsabilidad)" + //
                        "SELECT id,0 FROM Usuario WHERE nickname = ?;",nickname,correo,password,direccion,fechaSQL,nickname);
    }

    
    /**
     * La función `readOne` recupera un objeto `Comprador` de la base de datos basándose en un
     * identificador determinado (apodo o correo electrónico).
     * 
     * @param identifier El parámetro "identificador" se utiliza para buscar un usuario específico en
     * la base de datos según su apodo o dirección de correo electrónico. La consulta SQL recupera
     * información sobre el usuario, como su apodo, correo electrónico, contraseña, dirección, fecha de
     * registro y puntos de responsabilidad. Se utiliza el `CompradorMapper`
     * @return El método `readOne` devuelve un objeto `Comprador` basado en el resultado de la consulta
     * de la base de datos. La consulta selecciona columnas específicas (`nickname`, `correo`,
     * `user_password`, `direccion`, `fecha_registro`, `puntos_responsabilidad`) de las tablas
     * `Usuario` y `Comprador` donde está el `id_usuario` en el `Comprador` partidos de mesa
     */
    public Comprador readOne(String identifier){
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro, c.puntos_responsabilidad\r\n" + //
                        "FROM Usuario u, Comprador c \r\n" + //
                        "WHERE c.id_usuario = u.id \tAND (u.nickname = ? OR u.correo = ?)",new CompradorMapper(),identifier,identifier);
    }

   /**
    * Esta función Java lee todos los objetos Comprador de la base de datos ejecutando una consulta SQL
    * y mapeando los resultados utilizando un CompradorMapper.
    * 
    * @return Se devuelve una Lista de objetos Comprador. El método lee datos de la base de datos
    * ejecutando una consulta que selecciona columnas específicas de las tablas Usuario y Comprador, y
    * luego asigna los resultados a objetos Comprador usando la clase CompradorMapper.
    */
    public List<Comprador> readAll(){
        return database.query("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro, c.puntos_responsabilidad\r\n" + //
                        "FROM Usuario u, Comprador c \r\n" + //
                        "WHERE c.id_usuario = u.id",new CompradorMapper());
    }


/**
 * El método "actualizar" toma un apodo y un mapa de atributos, luego actualiza los valores
 * correspondientes en la base de datos según el tipo de atributo.
 * 
 * @param nickname El parámetro `nickname` es una cadena que representa el apodo del usuario cuyos
 * atributos se actualizan en la base de datos.
 * @param atributos El parámetro `atributos` es un mapa que contiene nombres de atributos como claves y
 * sus valores correspondientes. El método itera sobre cada entrada en el mapa, verifica el tipo de
 * valor y actualiza el atributo correspondiente en la tabla de la base de datos "Usuario" para el
 * apodo dado.
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
     * La función "eliminar" elimina un usuario y los datos relacionados de varias tablas según el
     * apodo del usuario.
     * 
     * @param nickname El método "eliminar" que proporcionó se utiliza para eliminar un usuario y la
     * información relacionada de varias tablas en una base de datos según el apodo del usuario. El
     * método ejecuta una serie de sentencias SQL `DELETE` para eliminar registros del `Comprador`,
     * `Usuario`, `Carrito_Compra`, `
     */
    public void delete(String nickname){
        database.update("DELETE FROM Comprador\r\n" + //
                        "WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);\r\n" + //
                        "\r\n" + //
                        "DELETE FROM Usuario\r\n" + //
                        "WHERE nickname = ?;\r\n" + //
                        "\r\n" + //
                        "DELETE FROM Carrito_Compra\r\n" + //
                        "WHERE id_comprador IN(SELECT id FROM Usuario WHERE nickname = ?);\r\n" + //
                        "\r\n" + //
                        "DELETE FROM Guardar_Mas_Tarde\r\n" + //
                        "WHERE id IN(SELECT id FROM Usuario WHERE nickname = ?);\r\n" + //
                        "\r\n" + //
                        "DELETE FROM Lista_De_Deseos\r\n" + //
                        "WHERE id IN(SELECT id FROM Usuario WHERE nickname = ?);",nickname,nickname);
    }

    /**
     * La función getAllDatabases recupera todas las tablas y sus atributos de un esquema de base de
     * datos específico en Java.
     * 
     * @return Este método devuelve una lista de cadenas, donde cada cadena representa información
     * sobre una tabla de base de datos en 'FiveGuysDatabase'. Cada cadena incluye el nombre de la
     * tabla y sus atributos.
     */
    public List<String> getAllDatabases() {
        List<String> result = new ArrayList<>();
        String sql = 
                     "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'FiveGuysDatabase' AND TABLE_TYPE = 'BASE TABLE'";
        List<String> tables = database.queryForList(sql, String.class);
        for (String table : tables) {
            String attributesSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table + "'";
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
