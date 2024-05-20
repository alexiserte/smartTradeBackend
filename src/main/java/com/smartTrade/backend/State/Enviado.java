package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class Enviado implements EstadoPedido{

    public void confirmar(Pedido pedido) {
        /*
         *  No se puede confirmar un pedido enviado.
         * */
    }

    @Override
    public void siguienteEstado(Pedido pedido) {
        pedido.setEstado(EstadosPedido.EN_REPARTO);
    }

    @Override
    public void estadoAnterior(Pedido pedido) {
        pedido.setEstado(EstadosPedido.PROCESANDO);
    }

    @Override
    public void procesar(Pedido pedido) {

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(EstadosPedido.CANCELADO);
    }

    @Override
    public String printPedidoState(Pedido pedido) {
        return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.ENVIADO.getNombreEstado() + ". " + EstadosPedido.ENVIADO.getDescripcionEstado();
    }
}
