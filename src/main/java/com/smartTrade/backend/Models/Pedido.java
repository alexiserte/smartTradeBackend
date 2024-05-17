package com.smartTrade.backend.Models;

import com.smartTrade.backend.State.EstadosPedido;
import java.util.List;
import java.sql.Date;

public class Pedido{
    private int id;
    private int id_comprador;
    private List<Producto> productos;
    private EstadosPedido estado;
    private Date fecha_realizacion;

    public Pedido(int id,int id_comprador, List<Producto> productos, Date fecha_realizacion){
        this.id = id;
        this.id_comprador = id_comprador;
        this.productos = productos;
        this.estado = EstadosPedido.PROCESANDO;
        this.fecha_realizacion = fecha_realizacion;
    }

    public Pedido(){}

    public int getId_comprador() {
        return id_comprador;
    }

    public void setId_comprador(int id_comprador) {
        this.id_comprador = id_comprador;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public String getProductosString(){
        String res = "";
        for(Producto p : productos){
            res += p.getNombre() + " ";
        }

        return res;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public EstadosPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadosPedido estado) {
        this.estado = estado;
    }

    public Date getFecha_realizacion() {
        return fecha_realizacion;
    }

    public void setFecha_realizacion(Date fecha_realizacion) {
        this.fecha_realizacion = fecha_realizacion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}