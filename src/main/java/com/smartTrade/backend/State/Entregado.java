package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class Entregado implements EstadoPedido{


        public void confirmar(Pedido pedido) {
            /*
             *  No se puede confirmar un pedido entregado.
             * */
        }

        /**
         *  No se puede pasar a un estado siguiente si ya se encuentra en el último estado.
         * */
        public void siguienteEstado(Pedido pedido) {
        }

        public void estadoAnterior(Pedido pedido) {
            pedido.setEstado(EstadosPedido.EN_REPARTO);
        }

        public void procesar(Pedido pedido) {

        }

        /**
         *  No se puede cancelar un pedido entregado.
         * */
        public void cancelar(Pedido pedido) {;}

        public String printPedidoState(Pedido pedido) {
            return "El pedido " + pedido.getId() + " está en estado de " + EstadosPedido.ENTREGADO.getNombreEstado() + ". " + EstadosPedido.ENTREGADO.getDescripcionEstado();
        }
}
