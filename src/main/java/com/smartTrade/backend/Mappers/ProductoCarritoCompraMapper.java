package com.smartTrade.backend.Mappers;

import com.smartTrade.backend.Factory.UserFactory;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.ProductoCarrito;
import com.smartTrade.backend.Models.User_Types;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoCarritoCompraMapper implements RowMapper<ProductoCarrito> {

    @Override
    public ProductoCarrito mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductoCarrito productoCarritto = new ProductoCarrito();
        productoCarritto.setId_carrito(rs.getInt("id_carrito"));
        productoCarritto.setId_producto(rs.getInt("id_producto"));
        productoCarritto.setId_vendedor(rs.getInt("id_vendedor"));
        productoCarritto.setCantidad(rs.getInt("cantidad"));
        return productoCarritto;
    }
}