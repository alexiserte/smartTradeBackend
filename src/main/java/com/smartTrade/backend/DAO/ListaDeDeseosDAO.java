package com.smartTrade.backend.DAO;
import java.util.List;


import com.smartTrade.backend.Mappers.ProductoListaDeseosMapper;
import com.smartTrade.backend.Models.ProductoListaDeseos;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ListaDeDeseosDAO implements DAOInterface<Object>{
    
    private JdbcTemplate database;

    public ListaDeDeseosDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(Object ...args) {
        String compradorName = (String) args[0];
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Lista_De_Deseos(id_comprador) VALUES (?)",id_comprador);
    }

    public List<ProductoListaDeseos> getListaDeseosFromUser(String nickname){
        return database.query("SELECT id_lista_de_deseos,id_producto, id_vendedor FROM Productos_Lista_Deseos WHERE id_lista_de_deseos IN(SELECT id FROM Lista_De_Deseos WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?))", new ProductoListaDeseosMapper(), nickname);
    }

    public void insertarProducto(String userNickname,String productName,String vendorName){
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_producto = database.queryForObject("SELECT id_producto FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_lista_de_deseos = database.queryForObject("SELECT id FROM Lista_De_Deseos WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("INSERT INTO Productos_Lista_Deseos(id_producto,id_lista_de_deseos,id_vendedor) VALUES (?,?,?)",id_producto,id_lista_de_deseos,id_vendedor);
    }

    public void deleteProducto(String userNickname,String productName,String vendorName){
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_producto = database.queryForObject("SELECT id_producto FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_lista_de_deseos = database.queryForObject("SELECT id FROM Lista_De_Deseos WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("DELETE FROM Productos_Lista_Deseos WHERE id_producto = ? AND id_lista_de_deseos = ? AND id_vendedor = ?",id_producto,id_lista_de_deseos,id_vendedor);
    }

    public void vaciarLista(String userNickname){
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
        int id_lista_de_deseos = database.queryForObject("SELECT id FROM Lista_De_Deseos WHERE id_comprador = ?", Integer.class, id_comprador);
        database.update("DELETE FROM Productos_Lista_Deseos WHERE id_id_lista_de_deseos = ?",id_lista_de_deseos);

    }

    public boolean productInListaDeseos(String productName,String vendorName,String userNickname){
        int id_listaDeseos,id_producto,id_vendedor;
        id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        id_listaDeseos = database.queryForObject("SELECT id FROM Lista_De_Deseos WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        try {
            ProductoListaDeseos producto = database.queryForObject("SELECT id_lista_de_deseos,id_producto, id_vendedor, cantidad FROM Productos_Lista_Deseos WHERE id_lista_de_deseos = ? AND id_producto = ? AND id_vendedor = ?", new ProductoListaDeseosMapper(), id_listaDeseos, id_producto, id_vendedor);
        }catch(EmptyResultDataAccessException e){
            return false;
        }
        return true;
    }

    public double getTotalPrice(String nickname){
        List<ProductoListaDeseos> productos = database.query("SELECT id_lista_de_deseos,id_producto, id_vendedor, cantidad FROM Productos_Lista_Deseos WHERE id_lista_de_deseos IN(SELECT id FROM Lista_De_Deseos WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?))", new ProductoListaDeseosMapper(), nickname);
        double total = 0;
        for(ProductoListaDeseos producto : productos){
            double precioProducto = database.queryForObject("SELECT precio FROM Vendedores_Producto WHERE id_vendedor = ? AND id_producto = ?", Double.class, producto.getId_vendedor(),producto.getId_producto());
        }
        return total;
    }

    public int productosInListaDeseos(String userNickname){
        int id_listaDeseos = database.queryForObject("SELECT id FROM Lista_De_Deseos WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        return database.queryForObject("SELECT COUNT(*) FROM Productos_Lista_Deseos WHERE id_lista_de_deseos = ?",Integer.class,id_listaDeseos);
    }

    public void deleteProduct(String productName, String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_listaDeseos = database.queryForObject("SELECT id FROM Lista_De_Deseos WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("DELETE FROM Productos_Lista_Deseos WHERE id_lista_de_deseos = ? AND id_producto = ? AND id_vendedor = ?",id_listaDeseos,id_producto, id_vendedor);
    }




    public void delete(Object ...args) {;}
    public void update(Object ...args) {;}
    public Object readOne(Object ...args) {return null;}
    public List<Object> readAll() {return null;}
    

}
