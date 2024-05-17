package com.smartTrade.backend.State;

import com.smartTrade.backend.Models.Pedido;

public class EsperandoConfirmacion implements EstadoPedido{
    private Pedido pedido;


    public EsperandoConfirmacion(Pedido pedido){
        this.pedido = pedido;
    }

    public void siguienteEstado(Pedido pedido) {

    }

    public void estadoAnterior(Pedido pedido){

    };
    public EstadosPedido getEstado(Pedido pedido){
        return null;
    };
    public void setEstado(Pedido pedido, EstadosPedido estado){};
    public void procesar(Pedido pedido){};
}
