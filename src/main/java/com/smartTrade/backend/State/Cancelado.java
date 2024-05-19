package com.smartTrade.backend.State;

import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Pedido;

public class Cancelado implements EstadoPedido{

    private Logger logger = Logger.getInstance();

    public void siguienteEstado(Pedido pedido) {
        /*
        *   No se puede pasar a otro estado.
        * */
    }

    public void cancelar(Pedido pedido){
        /*
        *   No se puede cancelar un pedido ya cancelado.
        *
        * */
    }

    public String printPedidoState(Pedido pedido){
        String message = "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.CANCELADO;
        logger.log(message);
        return message;
    }

    public void estadoAnterior(Pedido pedido){
        /*
        *   No se puede volver a un estado anterior.
        * */
    };

    public EstadosPedido getEstado(Pedido pedido){
        return EstadosPedido.CANCELADO;
    };

    public void setEstado(Pedido pedido, EstadosPedido estado){
        /*
        *   No se puede cambiar el estado de un pedido cancelado.
        * */
    };

    public void procesar(Pedido pedido){
        /*
        *   No se puede procesar un pedido cancelado.
        * */
    };
}
