package com.smartTrade.backend.State;

public enum EstadosPedido{
    ESPERANDO_CONFIRMACION("Esperando confirmación", "El pedido está pendiente de confirmación."),
    PROCESANDO("Procesando", "El pedido está siendo procesado."),
    ENVIADO("Enviado", "El pedido ha sido enviado."),
    EN_REPARTO("En reparto", "El pedido está en proceso de entrega."),
    ENTREGADO("Recibido", "El pedido ha sido entregado."),
    CANCELADO("Cancelado", "El pedido ha sido cancelado.");

    private final String nombreEstado;
    private final String descripcionEstado;

    EstadosPedido(String nombreEstado, String descripcionEstado) {
        this.nombreEstado = nombreEstado;
        this.descripcionEstado = descripcionEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public static EstadosPedido getEstado(String nombreEstado){
        for(EstadosPedido e : EstadosPedido.values()){
            if(e.getNombreEstado().equals(nombreEstado)){
                return e;
            }
        }
        return null;
    }
}