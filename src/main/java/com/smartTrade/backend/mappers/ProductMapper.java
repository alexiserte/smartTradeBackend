package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.models.Product_Types;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.daos.Caracteristica_ProductoDAO;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.factory.ProductFactory;

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
        return producto;
    }
}