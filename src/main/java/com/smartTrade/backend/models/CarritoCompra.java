package com.smartTrade.backend.models;

public class CarritoCompra {
    private int id_carrito;
    private int id_comprador;
    
    public CarritoCompra(int id_carrito, int id_comprador) {
        this.id_carrito = id_carrito;
        this.id_comprador = id_comprador;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_comprador() {
        return id_comprador;
    }

    public void setId_comprador(int id_comprador) {
        this.id_comprador = id_comprador;
    }
}
