package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class Entregado implements EstadoPedido {


    public boolean confirmar(Pedido pedido) {
        /*
         *  No se puede confirmar un pedido entregado.
         * */
        return false;
    }

    /**
     * No se puede pasar a un estado siguiente si ya se encuentra en el último estado.
     */
    public boolean siguienteEstado(Pedido pedido) {
        return false;
    }

    public boolean estadoAnterior(Pedido pedido) {
        pedido.setEstado(EstadosPedido.EN_REPARTO);
        return true;
    }

    public boolean procesar(Pedido pedido) {
        return false;
    }

    /**
     * No se puede cancelar un pedido entregado.
     */
    public boolean cancelar(Pedido pedido) {
        return false;
    }

    public String printPedidoState(Pedido pedido) {
        return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.ENTREGADO.getNombreEstado() + ". " + EstadosPedido.ENTREGADO.getDescripcionEstado();
    }
}
