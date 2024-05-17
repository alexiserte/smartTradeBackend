package com.smartTrade.backend.Models;

import com.smartTrade.backend.State.EstadosPedido;

import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.Map;

public class Pedido{
    private int id;
    private int id_comprador;
    private Map<Producto,Integer> productos;
    private EstadosPedido estado;
    private Date fecha_realizacion;
    private double precio_total;

    public Pedido(int id,int id_comprador, Map<Producto,Integer> productos, Date fecha_realizacion,double precio_total){
        this.id = id;
        this.id_comprador = id_comprador;
        this.productos = productos;
        this.estado = EstadosPedido.PROCESANDO;
        this.fecha_realizacion = fecha_realizacion;
        this.precio_total = precio_total;
    }

    public Pedido(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_comprador() {
        return id_comprador;
    }

    public void setId_comprador(int id_comprador) {
        this.id_comprador = id_comprador;
    }

    public Map<Producto,Integer> getProductos() {
        return productos;
    }

    public String getProductosString(){
        String res = "";
        for(Producto p : productos.keySet()){
            res += p.getNombre() + " x" + productos.get(p) + "\n";
        }

        return res;
    }

    public void setProductos(Map<Producto,Integer> productos) {
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


    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }


}