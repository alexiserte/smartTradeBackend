package com.smartTrade.backend.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.smartTrade.backend.Factory.ProductFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.DAO.Caracteristica_ProductoDAO;
import com.smartTrade.backend.DAO.VendedorDAO;
import com.smartTrade.backend.Models.Producto;

public class ProductMapper implements RowMapper<Producto> {

    @Override
    public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
        /*
        Producto producto = new Producto();
        producto.setId_categoria(rs.getInt("id_categoria"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setNombre(rs.getString("nombre"));
        producto.setFecha_publicacion(rs.getDate("fecha_añadido"));
        producto.setValidado(rs.getBoolean("validado"));
        producto.setHuella_ecologica(rs.getInt("huella_ecologica"));
        producto.setId_imagen(rs.getInt("id_imagen"));
        producto.setStock(rs.getInt("stock"));
        producto.setEtiqueta_inteligente(rs.getString("etiqueta_inteligente"));
        return producto;
        VERSION UTILIZANDO LA MANERA CLÁSICA
        */
        //Product Factory

        int id_categoria = rs.getInt("id_categoria");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        java.sql.Date fecha_publicacion = rs.getDate("fecha_añadido");
        boolean validado = rs.getBoolean("validado");
        int huella_ecologica = rs.getInt("huella_ecologica");
        int id_imagen = rs.getInt("id_imagen");
        int stock = rs.getInt("stock");
        String etiqueta_inteligente = rs.getString("etiqueta_inteligente");
        ProductFactory productFactory = new ProductFactory();
        Producto producto = productFactory.getProduct(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica, id_imagen, stock, etiqueta_inteligente);
        return producto;
    }

    public static void main(String[] args) {
        ProductFactory productFactory = new ProductFactory();
        Producto producto = productFactory.getProduct("nombre", "descripcion", 1, new java.sql.Date(0), true, 1, 1, 1, "etiqueta_inteligente");
        System.out.println(producto.getClass().getSimpleName());
        Producto producto2 = productFactory.getProduct("nombre", "descripcion", 5, new java.sql.Date(0), true, 1, 1, 1, "etiqueta_inteligente");
        System.out.println(producto2.getClass().getSimpleName());
        Producto producto3 = productFactory.getProduct("nombre", "descripcion", 8, new java.sql.Date(0), true, 1, 1, 1, "etiqueta_inteligente");
        System.out.println(producto3.getClass().getSimpleName());
        Producto producto4 = productFactory.getProduct("nombre", "descripcion", 9, new java.sql.Date(0), true, 1, 1, 1, "etiqueta_inteligente");
        System.out.println(producto4.getClass().getSimpleName());

    }
}