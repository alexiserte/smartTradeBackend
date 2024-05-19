package com.smartTrade.backend.Models;

import com.smartTrade.backend.State.*;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.Map;


public class Pedido{
     public static class ItemPedido{
        private Producto producto;
        private int cantidad;
        private String vendedor;
        private Date fecha_entrega;

        public ItemPedido(Producto producto, int cantidad, String vendedor, Date fecha_entrega) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.vendedor = vendedor;
            this.fecha_entrega = fecha_entrega;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public String getVendedor() {
            return vendedor;
        }

        public void setVendedor(String vendedor) {
            this.vendedor = vendedor;
        }

        public Date getFecha_entrega() {
            return fecha_entrega;
        }

        public void setFecha_entrega(Date fecha_entrega) {
            this.fecha_entrega = fecha_entrega;
        }

    }
    private int id;
    private int id_comprador;
    private List<ItemPedido> productos;
    private EstadosPedido estadoActual;
    private Date fecha_realizacion;
    private double precio_total;
    private EstadoPedido estado;

    public Pedido(int id,int id_comprador, List<ItemPedido> productos, Date fecha_realizacion,double precio_total){
        this.id = id;
        this.id_comprador = id_comprador;
        this.productos = productos;
        this.estado = new EsperandoConfirmacion();
        this.estadoActual = EstadosPedido.ESPERANDO_CONFIRMACION;
        this.fecha_realizacion = fecha_realizacion;
        this.precio_total = precio_total;
    }

    public Pedido(){
        this.estado = new EsperandoConfirmacion();
        this.estadoActual = EstadosPedido.ESPERANDO_CONFIRMACION;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getId_comprador() {return id_comprador;}

    public void setId_comprador(int id_comprador) {this.id_comprador = id_comprador;}

    public List<ItemPedido> getProductos() {return productos;}

    public void setProductos(List<ItemPedido> productos) {this.productos = productos;}

    public EstadosPedido getEstadoActual() {return estadoActual;}

    public void setEstadoActual(EstadosPedido estado) {this.estadoActual = estado;}

    public void setEstado(EstadoPedido estado) {this.estado = estado;}

    public void setEstado(EstadosPedido estado) {
        switch (estado){
            case ESPERANDO_CONFIRMACION:
                this.estado = new EsperandoConfirmacion();
                break;
            case PROCESANDO:
                this.estado = new Procesando();
                break;
            case ENVIADO:
                this.estado = new Enviado();
                break;
            case ENTREGADO:
                this.estado = new Entregado();
                break;
            case CANCELADO:
                this.estado = new Cancelado();
                break;
            case EN_REPARTO:
                this.estado = new EnReparto();
                break;
            default:
                this.estado = null;
        }
    }

    public EstadoPedido getEstado() {return estado;}

    public Date getFecha_realizacion() {return fecha_realizacion;}

    public void setFecha_realizacion(Date fecha_realizacion) {this.fecha_realizacion = fecha_realizacion;}

    public double getPrecio_total() {return precio_total;}

    public void setPrecio_total(double precio_total) {this.precio_total = precio_total;}



    // Implementacion del patron Estado

    public void siguienteEstado() {estado.siguienteEstado(this);}

    public void estadoAnterior() {estado.estadoAnterior(this);}

    public void procesar() {estado.procesar(this);}

    public void cancelar() {estado.cancelar(this);}

    public String printPedidoState() {return estado.printPedidoState(this);}

}