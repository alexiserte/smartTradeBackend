package com.smartTrade.backend.State;

import com.mysql.cj.log.Log;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Pedido;

public class Procesando implements EstadoPedido{

    private Logger logger = Logger.getInstance();

    @Override 
    public void siguienteEstado(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.ENVIADO);
        pedido.setEstado(new Enviado());
    }

    @Override
    public void estadoAnterior(Pedido pedido) {
        pedido.setEstadoActual(EstadosPedido.ESPERANDO_CONFIRMACION);
        pedido.setEstado(new EsperandoConfirmacion());
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
        String message = "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.PROCESANDO.getNombreEstado() + ". " + EstadosPedido.PROCESANDO.getDescripcionEstado();
        logger.log(message);
        return message;
    }
}
