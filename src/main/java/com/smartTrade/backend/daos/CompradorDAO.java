package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.CompradorMapper;
import com.smartTrade.backend.models.Comprador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class CompradorDAO{
    
    private JdbcTemplate database;

    public CompradorDAO(JdbcTemplate database) {
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
    public void create(String nickname, String password, String correo, String direccion) {

        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

        database.update("INSERT INTO Usuario(nickname, correo, user_password, direccion, fecha_registro) " +
                "VALUES (?, ?, ?, ?, ?);INSERT INTO Comprador(id_usuario, puntos_responsabilidad) " +
                "SELECT id, 0 FROM Usuario WHERE nickname = ?;INSERT INTO Carrito_Compra(id_comprador) " +
                "SELECT id FROM Usuario WHERE nickname = ?; INSERT INTO Guardar_Mas_Tarde(id_comprador) " +
                "SELECT id FROM Usuario WHERE nickname = ?; INSERT INTO Lista_De_Deseos(id_comprador) " +
                "SELECT id FROM Usuario WHERE nickname = ?;",
                nickname, correo, password, direccion, fechaSQL, nickname, nickname, nickname, nickname);

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
        return database.queryForObject("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro, c.puntos_responsabilidad " + //
                        "FROM Usuario u, Comprador c " + //
                        "WHERE c.id_usuario = u.id AND (u.nickname = ? OR u.correo = ?)",new CompradorMapper(),identifier,identifier);
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
        return database.query("SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro, c.puntos_responsabilidad" + //
                        "FROM Usuario u, Comprador c " + //
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
        database.update("DELETE FROM Comprador" + //
                        "WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);" + //
                        "" + //
                        "DELETE FROM Usuario" + //
                        "WHERE nickname = ?;" + //
                        "" + //
                        "DELETE FROM Carrito_Compra" + //
                        "WHERE id_comprador IN(SELECT id FROM Usuario WHERE nickname = ?);" + //
                        "" + //
                        "DELETE FROM Guardar_Mas_Tarde" + //
                        "WHERE id IN(SELECT id FROM Usuario WHERE nickname = ?);" + //
                        "" + //
                        "DELETE FROM Lista_De_Deseos" + //
                        "WHERE id IN(SELECT id FROM Usuario WHERE nickname = ?);",nickname,nickname);
    }

}
