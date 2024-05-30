package com.smartTrade.backend.Mappers;

import com.smartTrade.backend.Models.ProductoGuardarMasTarde;
import com.smartTrade.backend.Models.ProductoListaDeseos;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoGuardarMasTardeMapper implements RowMapper<ProductoGuardarMasTarde> {

    @Override
    public ProductoGuardarMasTarde mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductoGuardarMasTarde productoListaDeseos = new ProductoGuardarMasTarde();
        productoListaDeseos.setId_guardar_mas_tarde(rs.getInt("id_guardar_mas_tarde"));
        productoListaDeseos.setId_producto(rs.getInt("id_producto"));
        productoListaDeseos.setId_vendedor(rs.getInt("id_vendedor"));
        return productoListaDeseos;
    }
}
