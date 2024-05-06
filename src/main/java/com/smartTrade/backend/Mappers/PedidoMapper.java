package com.smartTrade.backend.Mappers;


import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PedidoMapper implements RowMapper<Pedido> {

    private List<Producto> productos;
    public PedidoMapper( List<Producto> productos){
        this.productos = productos;
    }
    
    @Override
    public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setFecha_realizacion(rs.getDate("fecha_realizacion"));
        pedido.setId_comprador(rs.getInt("id_comprador"));
        pedido.setProductos(productos);
        return pedido;
    }
}