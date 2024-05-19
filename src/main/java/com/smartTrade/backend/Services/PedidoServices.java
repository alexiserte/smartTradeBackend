package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.PedidoDAO;
import com.smartTrade.backend.DAO.UsuarioDAO;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PedidoServices {

    @Autowired
    PedidoDAO pedidoDAO;

    @Autowired
    UsuarioDAO usuarioDAO;

    public void createNewPedido(String nickname, Map<Pair<Producto, String>, Integer> productos,double precio_total){
        pedidoDAO.create(Map.of("nickname", nickname, "productos", productos, "precio_total", precio_total));
    }

    public Pedido readOnePedido(int id) {
        return pedidoDAO.readOne(Map.of("id", id));
    }

    public List<Pedido> readAllPedidos() {
        return pedidoDAO.readAll();
    }


    public List<Pedido> readPedidosFromUser(String nickname){
        int id_comprador = usuarioDAO.getID(nickname);
        List<Pedido> pedidos = pedidoDAO.readAll();
        pedidos.removeIf(pedido -> pedido.getId_comprador() != id_comprador);
        return pedidos;
    }
}
