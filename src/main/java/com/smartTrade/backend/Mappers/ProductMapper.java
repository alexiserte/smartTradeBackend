package com.smartTrade.backend.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.DAO.Caracteristica_ProductoDAO;
import com.smartTrade.backend.DAO.VendedorDAO;
import com.smartTrade.backend.Models.Producto;

public class ProductMapper implements RowMapper<Producto> {

    @Autowired
    Caracteristica_ProductoDAO caracteristicaProductoDAO;
    @Autowired
    VendedorDAO vendedorDAO;
    @Override
    public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Producto producto = new Producto();
        producto.setId_categoria(rs.getInt("id_categoria"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setNombre(rs.getString("nombre"));
        producto.setFecha_publicacion(rs.getDate("fecha_añadido"));
        producto.setValidado(rs.getBoolean("validado"));
        producto.setHuella_ecologica(rs.getInt("huella_ecologica"));
        producto.setId_imagen(rs.getInt("id_imagen"));
        producto.setStock(rs.getInt("stock"));
        return producto;
    }
}