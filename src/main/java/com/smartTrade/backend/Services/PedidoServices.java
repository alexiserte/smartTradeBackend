package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.*;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.State.EstadosPedido;
import com.smartTrade.backend.Utils.CountriesMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoServices {

    @Autowired
    PedidoDAO pedidoDAO;

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    CountryDAOAndServices countryDAO;

    @Autowired
    CompradorDAO compradorDAO;

    @Autowired
    VendedorDAO vendedorDAO;

    //String nickname, Map<Pair<Producto, String>, Integer> productos,double precio_total

    public void createNewPedido(HashMap<String, ?> body){
        String nickname = (String) body.get("nickname");
        ArrayList<Map<String,Producto>> productos = (ArrayList<Map<String,Producto>>) body.get("productos");
        String precio_total = (String) body.get("precio_total");
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

    public List<Producto> getProductosFromOnePedidoGivenTheID(int id){
        List<Pedido> pedidos = readAllPedidos();
        List<Producto> res = new ArrayList<>();
        List<Pedido> filtered = pedidos
                                .stream()
                                .filter(pedido -> pedido.getId() == id)
                                .toList();
        if(filtered.size() == 0) return res;
        List<Pedido.ItemPedido> items = filtered.get(0).getProductos();
        for(Pedido.ItemPedido item : items){
            res.add(item.getProducto());
        }
        return res;

    }
}
