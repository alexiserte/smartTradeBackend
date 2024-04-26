package com.smartTrade.backend.Models;

public class Pedido{

    private int id_pedido;
    private int id_consumidor;
    private String articulo;
    private int cantidad_de_producto;

    public Pedido(int id_pedido, int id_consumidor, String articulo, int cantidad_de_producto) {
        this.id_pedido = id_pedido;
        this.id_consumidor = id_consumidor;
        this.articulo = articulo;
        this.cantidad_de_producto = cantidad_de_producto;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_consumidor() {
        return id_consumidor;
    }

    public void setId_consumidor(int id_consumidor) {
        this.id_consumidor = id_consumidor;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public int getCantidad_de_producto() {
        return cantidad_de_producto;
    }

    public void setCantidad_de_producto(int cantidad_de_producto) {
        this.cantidad_de_producto = cantidad_de_producto;
    }
}