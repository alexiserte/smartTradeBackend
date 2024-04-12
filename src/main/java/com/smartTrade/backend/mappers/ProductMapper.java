package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Producto;

public class ProductMapper implements RowMapper<Producto> {
    @Override
    public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Producto producto = new Producto();
        producto.setId_vendedor(rs.getInt("id_vendedor"));
        producto.setId_categoria(rs.getInt("id_categoria"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setNombre(rs.getString("nombre"));
        producto.setImagen(rs.getString("imagen"));
        producto.setFecha_publicacion(rs.getDate("fecha_añadido"));
        return producto;
    }
}