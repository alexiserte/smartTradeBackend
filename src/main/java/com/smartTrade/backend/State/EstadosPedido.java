package com.smartTrade.backend.State;

public enum EstadosPedido{
    ESPERANDO_CONFIRMACION("Esperando confirmación", "El pedido está pendiente de confirmación.", 1),
    PROCESANDO("Procesando", "El pedido está siendo procesado.",2),
    ENVIADO("Enviado", "El pedido ha sido enviado.",3),
    EN_REPARTO("En reparto", "El pedido está en proceso de entrega.",4),
    ENTREGADO("Recibido", "El pedido ha sido entregado.",5),
    CANCELADO("Cancelado", "El pedido ha sido cancelado.",0);

    private final String nombreEstado;
    private final String descripcionEstado;
    private int id;

    EstadosPedido(String nombreEstado, String descripcionEstado, int id) {
        this.nombreEstado = nombreEstado;
        this.descripcionEstado = descripcionEstado;
        this.id = id;
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

    public int getId() {
        return id;
    }
}