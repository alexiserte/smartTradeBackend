package com.smartTrade.backend.State;

import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Pedido;

public class Procesando implements EstadoPedido {

    private final Logger logger = Logger.getInstance();


    public boolean confirmar(Pedido pedido) {
        /*
         *  No se puede confirmar un pedido en proceso.
         * */
        return false;
    }

    @Override
    public boolean siguienteEstado(Pedido pedido) {
        pedido.setEstado(EstadosPedido.ENVIADO);
        return true;
    }

    @Override
    public boolean estadoAnterior(Pedido pedido) {
        pedido.setEstado(EstadosPedido.ESPERANDO_CONFIRMACION);
        return true;
    }


    @Override
    public boolean procesar(Pedido pedido) {
        /*
         *  No se puede procesar un pedido en proceso.
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
        String message = "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.PROCESANDO.getNombreEstado() + ". " + EstadosPedido.PROCESANDO.getDescripcionEstado();
        logger.log(message);
        return message;
    }
}
