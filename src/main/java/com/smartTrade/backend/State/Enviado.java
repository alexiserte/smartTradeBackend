package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class Enviado implements EstadoPedido{

    public boolean confirmar(Pedido pedido) {
        /*
         *  No se puede confirmar un pedido enviado.
         * */
        return false;
    }

    @Override
    public boolean siguienteEstado(Pedido pedido) {
        pedido.setEstado(EstadosPedido.EN_REPARTO);
        return true;
    }

    @Override
    public boolean estadoAnterior(Pedido pedido) {
        pedido.setEstado(EstadosPedido.PROCESANDO);
        return true;
    }

    @Override
    public boolean procesar(Pedido pedido) {
        /*
         *  No se puede procesar un pedido enviado.
         * */
        return false;
    }

    @Override
    public boolean cancelar(Pedido pedido) {
        pedido.setEstado(EstadosPedido.CANCELADO);
        return true;
    }

    @Override
    public String printPedidoState(Pedido pedido) {
        return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.ENVIADO.getNombreEstado() + ". " + EstadosPedido.ENVIADO.getDescripcionEstado();
    }
}
