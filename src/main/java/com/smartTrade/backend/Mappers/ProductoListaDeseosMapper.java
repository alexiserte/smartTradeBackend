package com.smartTrade.backend.Mappers;

import com.smartTrade.backend.Factory.UserFactory;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.ProductoCarrito;
import com.smartTrade.backend.Models.ProductoListaDeseos;
import com.smartTrade.backend.Models.User_Types;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoListaDeseosMapper implements RowMapper<ProductoListaDeseos> {
    
    @Override
    public ProductoListaDeseos mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductoListaDeseos productoListaDeseos = new ProductoListaDeseos();
        productoListaDeseos.setId_listaDeseos(rs.getInt("id_lista_de_deseos"));
        productoListaDeseos.setId_producto(rs.getInt("id_producto"));
        productoListaDeseos.setId_vendedor(rs.getInt("id_vendedor"));
        return productoListaDeseos;
    }
}