package com.smartTrade.backend.State;

import com.mysql.cj.log.Log;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class EsperandoConfirmacion implements EstadoPedido{

    private Logger logger = Logger.getInstance();
    private static final EstadosPedido estado = EstadosPedido.ESPERANDO_CONFIRMACION;


    public boolean confirmar(Pedido pedido) {
        siguienteEstado(pedido);
        return true;
    }

    public boolean siguienteEstado(Pedido pedido) {
        pedido.setEstado(EstadosPedido.PROCESANDO);
        return true;
    }

    public boolean cancelar(Pedido pedido){
        pedido.setEstado(EstadosPedido.CANCELADO);
        return true;
    }

    public String printPedidoState(Pedido pedido){
        String message = "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.ESPERANDO_CONFIRMACION + ". " + EstadosPedido.ESPERANDO_CONFIRMACION.getDescripcionEstado();
        logger.log(message);
        return message;
    }
    public boolean estadoAnterior(Pedido pedido){
        /*
        *   No se puede volver al estado anterior
        * */
        return false;
    };
    public boolean procesar(Pedido pedido){
        /*
        *   No se puede procesar un pedido en estado de espera
        * */
        return false;
    };

}
