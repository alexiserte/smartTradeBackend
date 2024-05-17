package com.smartTrade.backend.State;

public enum EstadosPedido{
    ESPERANDO_CONFIRMACION("Esperando confirmación", "El pedido está pendiente de confirmación."),
    PROCESANDO("Procesando", "El pedido está siendo procesado."),
    ENVIADO("Enviado", "El pedido ha sido enviado."),
    EN_REPARTO("En reparto", "El pedido está en proceso de entrega."),
    RECIBIDO("Recibido", "El pedido ha sido recibido por el cliente.");

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
}