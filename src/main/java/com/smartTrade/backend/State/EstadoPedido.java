package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public interface EstadoPedido {
    void siguienteEstado(Pedido pedido);
    void estadoAnterior(Pedido pedido);
    EstadosPedido getEstado(Pedido pedido);
    void setEstado(Pedido pedido, EstadosPedido estado);
    void procesar(Pedido pedido);
}