package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;
import org.springframework.data.util.Pair;

public interface EstadoPedido {
    void siguienteEstado(Pedido pedido);
    void estadoAnterior(Pedido pedido);
    void procesar(Pedido pedido);
    void cancelar(Pedido pedido);
    String printPedidoState(Pedido pedido);
    void confirmar(Pedido pedido);
}