package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class EnReparto implements EstadoPedido {

    public boolean confirmar(Pedido pedido) {
        /*
         *   No se puede confirmar un pedido en reparto.
         * */
        return false;
    }

    @Override
    public boolean siguienteEstado(Pedido pedido) {
        pedido.setEstado(EstadosPedido.ENTREGADO);
        return true;
    }

    @Override
    public boolean estadoAnterior(Pedido pedido) {
        pedido.setEstado(EstadosPedido.ENVIADO);
        return true;
    }

    @Override
    public boolean procesar(Pedido pedido) {
        return false;
    }

    @Override
    public boolean cancelar(Pedido pedido) {
        pedido.setEstado(EstadosPedido.CANCELADO);
        return true;
    }

    @Override
    public String printPedidoState(Pedido pedido) {
        return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.EN_REPARTO.getNombreEstado() + ". " + EstadosPedido.EN_REPARTO.getDescripcionEstado();
    }
}
