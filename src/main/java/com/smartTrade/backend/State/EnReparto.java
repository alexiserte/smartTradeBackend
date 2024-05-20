package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class EnReparto implements EstadoPedido{

    public void confirmar(Pedido pedido){
        /*
        *   No se puede confirmar un pedido en reparto.
        * */
    }
    @Override
    public void siguienteEstado(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.ENTREGADO);
        pedido.setEstado(new Entregado());
    }

    @Override
    public void estadoAnterior(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.ENVIADO);
        pedido.setEstado(new Enviado());
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
        return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.EN_REPARTO.getNombreEstado() + ". " + EstadosPedido.EN_REPARTO.getDescripcionEstado();
    }
}
