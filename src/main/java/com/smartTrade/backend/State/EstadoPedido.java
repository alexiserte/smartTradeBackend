package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public interface EstadoPedido {
    boolean siguienteEstado(Pedido pedido);

    boolean estadoAnterior(Pedido pedido);

    boolean procesar(Pedido pedido);

    boolean cancelar(Pedido pedido);

    String printPedidoState(Pedido pedido);

    boolean confirmar(Pedido pedido);
}