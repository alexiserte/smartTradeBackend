package com.smartTrade.backend.Mappers;


import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.State.EstadosPedido;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.smartTrade.backend.Models.Pedido;


public class PedidoMapper implements RowMapper<Pedido> {

    private List<Pedido.ItemPedido> productos;
    public PedidoMapper( List<Pedido.ItemPedido> productos){
        this.productos = productos;
    }
    
    @Override
    public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        pedido.setId_comprador(rs.getInt("id_comprador"));
        pedido.setProductos(productos);
        pedido.setEstadoActual(EstadosPedido.getEstado(rs.getString("estado")));
        pedido.setEstado(pedido.getEstadoActual());
        pedido.setFecha_realizacion(rs.getDate("fecha_realizacion"));
        pedido.setPrecio_total(rs.getDouble("precio_total"));
        pedido.setFecha_entrega(rs.getDate("fecha_entrega"));
        return pedido;
    }
}