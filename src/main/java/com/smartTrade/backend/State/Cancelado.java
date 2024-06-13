package com.smartTrade.backend.State;

import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class Cancelado implements EstadoPedido {

    private Logger logger = Logger.getInstance();

    public boolean confirmar(Pedido pedido) {
        /*
         *   No se puede confirmar un pedido cancelado.
         * */
        return false;
    }

    public boolean siguienteEstado(Pedido pedido) {
        /*
         *   No se puede pasar a otro estado.
         * */
        return false;
    }

    public boolean cancelar(Pedido pedido) {
        /*
         *   No se puede cancelar un pedido ya cancelado.
         *
         * */
        return false;
    }

    public String printPedidoState(Pedido pedido) {
        String message = "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.CANCELADO;
        logger.log(message);
        return message;
    }

    public boolean estadoAnterior(Pedido pedido) {
        /*
         *   No se puede volver a un estado anterior.
         * */
        return false;
    }

    ;

    public boolean procesar(Pedido pedido) {
        /*
         *   No se puede procesar un pedido cancelado.
         * */
        return false;
    }

    ;
}
