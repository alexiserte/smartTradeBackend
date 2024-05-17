package com.smartTrade.backend.Mappers;


import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.State.EstadosPedido;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class PedidoMapper implements RowMapper<Pedido> {

    private Map<Producto,Integer> productos;
    public PedidoMapper( Map<Producto,Integer> productos){
        this.productos = productos;
    }
    
    @Override
    public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        pedido.setId_comprador(rs.getInt("id_comprador"));
        pedido.setProductos(productos);
        pedido.setEstado(EstadosPedido.valueOf(rs.getString("estado")));
        pedido.setFecha_realizacion(rs.getDate("fecha_realizacion"));
        pedido.setPrecio_total(rs.getDouble("precio_total"));
        return pedido;
    }
}