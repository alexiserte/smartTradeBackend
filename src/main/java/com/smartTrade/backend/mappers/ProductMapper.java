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
        producto.setDescripcion(rs.getString("nickname"));
        producto.setId_producto(rs.getInt("id_producto"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setMaterial(rs.getString("material"));
        return producto;
    }
}