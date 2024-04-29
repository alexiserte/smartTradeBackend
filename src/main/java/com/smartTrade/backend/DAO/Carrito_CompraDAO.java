package com.smartTrade.backend.DAO;
import java.util.List;
import java.sql.Date;

import com.smartTrade.backend.Mappers.ProductoCarritoCompraMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.*;

@Repository
public class Carrito_CompraDAO implements DAOInterface<Object>{
    
    private JdbcTemplate database;

    public Carrito_CompraDAO(JdbcTemplate database) {
        this.database = database;
    }
    

    public void create(Object ...args) {
        String compradorName = (String) args[0];
        int id_comprador = database.queryForObject("SELECT id_usuario FROM Usuario WHERE nickname = ?", Integer.class, compradorName);
        database.update("INSERT INTO Carrito_Compra(id_comprador) VALUES (?)",id_comprador);
    }

    public List<ProductoCarrito> getCarritoFromUser(String nickname){
        return database.query("SELECT id_carrito,id_producto, id_vendedor, cantidad FROM Productos_Carrito WHERE id_carrito IN(SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?))", new ProductoCarritoCompraMapper(), nickname);
    }

    public int getCantidadFromOneProduct(String productName,String vendorName, String nickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, nickname);
        return database.queryForObject("SELECT cantidad FROM Productos_Carrito WHERE id_producto = ? AND id_vendedor = ? AND id_carrito = ?", Integer.class, id_producto, id_vendedor, id_carrito);
        }

    public void insertarProduct(String productName,String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("INSERT INTO Productos_Carrito(id_carrito,id_producto,id_vendedor) VALUES(?,?,?)",id_carrito,id_producto,id_vendedor);
    }

    public void deleteProduct(String productName, String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("DELETE FROM Productos_Carrito WHERE id_carrito = ? AND id_producto = ? AND id_vendedor = ?",id_carrito,id_producto, id_vendedor);
    }

    public double getTotalPrice(String nickname){
        List<ProductoCarrito> productos = database.query("SELECT id_carrito,id_producto, id_vendedor, cantidad FROM Productos_Carrito WHERE id_carrito IN(SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?))", new ProductoCarritoCompraMapper(), nickname);
        double total = 0;
        for(ProductoCarrito producto : productos){
            double precioProducto = database.queryForObject("SELECT precio FROM Vendedores_Producto WHERE id_vendedor = ? AND id_producto = ?", Double.class, producto.getId_vendedor(),producto.getId_producto());
            total = total + (precioProducto * producto.getCantidad());
        }
        return total;
    }

    public void aumentarCantidad(String productName, String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("UPDATE Productos_Carrito SET cantidad = cantidad + 1 WHERE id_carrito = ? AND id_producto = ? AND id_vendedor = ?",id_carrito,id_producto, id_vendedor);
    }

    public void disminuirCantidad(String productName, String vendorName, String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        database.update("UPDATE Productos_Carrito SET cantidad = cantidad - 1 WHERE id_carrito = ? AND id_producto = ? AND id_vendedor = ?",id_carrito,id_producto, id_vendedor);
    }

    public boolean productInCarrito(String productName,String vendorName,String userNickname){
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        int id_vendedor = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, vendorName);
        try {
            boolean exists = database.queryForObject("SELECT COUNT(*) FROM Productos_Carrito WHERE id_carrito = ? AND id_producto = ? AND id_vendedor = ?", Integer.class, id_carrito, id_producto, id_vendedor) > 0;
            return true;
        }catch(EmptyResultDataAccessException e){
            return false;
        }
    }

    public double aplicarDescuento(String userNickname,String discountCode){
        double total = getTotalPrice(userNickname);
        double descuento;
        int usos;
        try {
            descuento = database.queryForObject("SELECT descuento FROM Codigo_Descuento WHERE codigo = ?", Double.class, discountCode);
            usos = database.queryForObject("SELECT usos FROM Codigo_Descuento WHERE codigo = ?", Integer.class, discountCode);
        }catch(EmptyResultDataAccessException e){
            throw new IllegalArgumentException("El código de descuento ingresado no es válido");
        }
        Date fecha_inicio = database.queryForObject("SELECT fecha_validez_inicio FROM Codigo_Descuento WHERE codigo = ?", java.sql.Date.class, discountCode);
        Date fecha_final = database.queryForObject("SELECT fecha_validez_final FROM Codigo_Descuento WHERE codigo = ?", java.sql.Date.class, discountCode);

        Date fecha_actual = new Date(System.currentTimeMillis());

        if(fecha_actual.before(fecha_inicio)){
            throw new IllegalArgumentException("El código de descuento aún no es válido");
        }
        else if(fecha_actual.after(fecha_final)){
            throw new IllegalArgumentException("El código de descuento ha expirado");
        }
        else{
            int id_comprador = database.queryForObject("SELECT id FROM Usuario WHERE nickname = ?", Integer.class, userNickname);
            int id_codigo = database.queryForObject("SELECT id FROM Codigo_Descuento WHERE codigo = ?", Integer.class, discountCode);
            int veces_usados = database.queryForObject("SELECT COUNT(*) FROM Codigos_Usados WHERE id_codigo = ? AND id_comprador = ?", Integer.class, id_codigo,id_comprador);
            if(veces_usados >= usos){
                throw new IllegalArgumentException("El código de descuento ha alcanzado su límite de usos");
            }
            else{
                database.update("INSERT INTO Codigos_Usados(id_codigo,id_comprador) VALUES(?,?)",id_codigo,id_comprador);
                return total - (total * descuento);
            }
        }
    }

    public void vaciarCarrito(String userNickname){
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        database.update("DELETE FROM Productos_Carrito WHERE id_carrito = ?",id_carrito);
    }

    public int productosInCarrito(String userNickname){
        int id_carrito = database.queryForObject("SELECT id FROM Carrito_Compra WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, userNickname);
        return database.queryForObject("SELECT COUNT(*) FROM Productos_Carrito WHERE id_carrito = ?",Integer.class,id_carrito);
    }

    public double getDiscount(String codigo){
        try {
            return database.queryForObject("SELECT descuento FROM Codigo_Descuento WHERE codigo = ?", Double.class, codigo);
        }catch(EmptyResultDataAccessException e){
            throw new IllegalArgumentException("El código de descuento ingresado no es válido");
        }
    }
    public void delete(Object ...args) {;}
    public void update(Object ...args) {;}
    public Object readOne(Object ...args) {return null;}
    public List<Object> readAll() {return null;}
    

}
