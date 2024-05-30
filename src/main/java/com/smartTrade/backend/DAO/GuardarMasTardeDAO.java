package com.smartTrade.backend.DAO;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Mappers.ProductoCarritoCompraMapper;
import com.smartTrade.backend.Mappers.ProductoGuardarMasTardeMapper;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoCarrito;
import com.smartTrade.backend.Models.ProductoGuardarMasTarde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class GuardarMasTardeDAO implements DAOInterface<Object>{
    
    private JdbcTemplate database;


    public GuardarMasTardeDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(Map<String,?> args) {
        String compradorName = (String) args.get("compradorName");
        int id_comprador = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Guardar_Mas_Tarde(id_comprador) VALUES (?)",id_comprador);
    }

    public void insertarProducto(Map<String,?> args){
        String userNickname = (String) args.get("userNickname");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        int id_comprador = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_guardar_mas_tarde = database.queryForObject("SELECT id_guardar_mas_tarde FROM Guardar_Mas_Tarde WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("INSERT INTO Productos_Guardar_Mas_Tarde(id_producto,id_guardar_mas_tarde,id_vendedor) VALUES (?,?,?)",id_producto,id_guardar_mas_tarde,id_vendedor);
    }

    public void deleteProducto(Map<String,?> args){
        String userNickname = (String) args.get("userNickname");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        int id_comprador = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_guardar_mas_tarde = database.queryForObject("SELECT id_guardar_mas_tarde FROM Guardar_Mas_Tarde WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("DELETE FROM Productos_Guardar_Mas_Tarde WHERE id_producto = ? AND id_guardar_mas_tarde = ? AND id_vendedor = ?",id_producto,id_guardar_mas_tarde,id_vendedor);
    }

    public void vaciarLista(Map<String,?> args){
        String userNickname = (String) args.get("userNickname");
        int id_comprador = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_guardar_mas_tarde = database.queryForObject("SELECT id_guardar_mas_tarde FROM Guardar_Mas_Tarde WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("DELETE FROM Productos_Guardar_Mas_Tarde WHERE id_guardar_mas_tarde = ?",id_guardar_mas_tarde);

    }

    public void delete(Map<String,?> args) {;}
    public void update(Map<String,?> args) {;}
    public Object readOne(Map<String,?> args) {
        String compradorName = (String) args.get("compradorName");
        int id_comprador = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        int id_guardar_mas_tarde = database.queryForObject("SELECT id_guardar_mas_tarde FROM Guardar_Mas_Tarde WHERE id_comprador = ?", Integer.class, id_comprador);

        List<Integer> id_productos = database.queryForList("SELECT id_producto FROM Productos_Guardar_Mas_Tarde WHERE id_guardar_mas_tarde = ?", Integer.class, id_guardar_mas_tarde);
        List<Producto> productos = new ArrayList<>();
        for(Integer id_product : id_productos){
            Producto producto = database.queryForObject("SELECT nombre, id_categoria, descripcion, id_imagen, fecha_añadido, validado, huella_ecologica, stock, etiqueta_inteligente FROM Producto WHERE id = ?", new ProductMapper(), id_product);
            productos.add(producto);
        }

        return Map.of("compradorName",compradorName,"productos",productos);

    }
    public List<Object> readAll() {return null;}


    public List<ProductoGuardarMasTarde> getGuardarMasTarde(String userNickname){
        return database.query("SELECT id_guardar_mas_tarde,id_producto, id_vendedor FROM Productos_Guardar_Mas_Tarde WHERE id_guardar_mas_tarde IN(SELECT id_guardar_mas_tarde FROM Guardar_Mas_Tarde WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?))", new ProductoGuardarMasTardeMapper(), userNickname);
    }

    public double getTotalPrice(String userName) {
        List<ProductoGuardarMasTarde> productos = database.query("SELECT id_guardar_mas_tarde,id_producto, id_vendedor FROM Productos_Guardar_Mas_Tarde WHERE id_guardar_mas_tarde IN(SELECT id_guardar_mas_tarde FROM Guardar_Mas_Tarde WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?))", new ProductoGuardarMasTardeMapper(), userName);
        double total = 0;
        for(ProductoGuardarMasTarde producto : productos){
            double precioProducto = database.queryForObject("SELECT precio FROM Vendedores_Producto WHERE id_vendedor = ? AND id_producto = ?", Double.class, producto.getId_vendedor(),producto.getId_producto());
            total += precioProducto;
        }
        return total;
    }
    

}
