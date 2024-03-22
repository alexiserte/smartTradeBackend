package com.smartTrade.backend.models;

public class Envio {
   private int id_envio;
   private int id_pedido;
   private String estado_envio;
   private float coste_total_productos_enviados;
   
    public Envio(int id_envio, int id_pedido, String estado_envio, float coste_total_productos_enviados) {
         this.id_envio = id_envio;
         this.id_pedido = id_pedido;
         this.estado_envio = estado_envio;
         this.coste_total_productos_enviados = coste_total_productos_enviados;
    }

    public int getId_envio() {
        return id_envio;
    }

    public void setId_envio(int id_envio) {
        this.id_envio = id_envio;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getEstado_envio() {
        return estado_envio;
    }

    public void setEstado_envio(String estado_envio) {
        this.estado_envio = estado_envio;
    }

    public float getCoste_total_productos_enviados() {
        return coste_total_productos_enviados;
    }

    public void setCoste_total_productos_enviados(float coste_total_productos_enviados) {
        this.coste_total_productos_enviados = coste_total_productos_enviados;
    }
}
