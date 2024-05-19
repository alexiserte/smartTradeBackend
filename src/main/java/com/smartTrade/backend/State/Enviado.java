package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class Enviado implements EstadoPedido{
    @Override
    public void siguienteEstado(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.EN_REPARTO);
        pedido.setEstado(new EnReparto());
    }

    @Override
    public void estadoAnterior(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.PROCESANDO);
        pedido.setEstado(new Procesando());
    }

    @Override
    public void procesar(Pedido pedido) {

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.CANCELADO);
        pedido.setEstado(new Cancelado());
    }

    @Override
    public String printPedidoState(Pedido pedido) {
        return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.ENVIADO.getNombreEstado() + ". " + EstadosPedido.ENVIADO.getDescripcionEstado();
    }
}
