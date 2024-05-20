package com.smartTrade.backend.State;

import com.mysql.cj.log.Log;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Pedido;

public class Procesando implements EstadoPedido{

    private Logger logger = Logger.getInstance();


    public void confirmar(Pedido pedido) {
        /*
         *  No se puede confirmar un pedido en proceso.
         * */
    }

    @Override 
    public void siguienteEstado(Pedido pedido) {
        pedido.setEstado(EstadosPedido.ENVIADO);
    }

    @Override
    public void estadoAnterior(Pedido pedido) {
        pedido.setEstado(EstadosPedido.ESPERANDO_CONFIRMACION);
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
        String message = "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.PROCESANDO.getNombreEstado() + ". " + EstadosPedido.PROCESANDO.getDescripcionEstado();
        logger.log(message);
        return message;
    }
}
