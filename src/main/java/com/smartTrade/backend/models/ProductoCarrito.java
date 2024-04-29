package com.smartTrade.backend.Models;

public class ProductoCarrito {
    private int id_carrito;
    private int id_producto;
    private int id_vendedor;
    private int cantidad;

    public ProductoCarrito(int id_carrito, int id_producto, int id_vendedor, int cantidad) {
        this.id_carrito = id_carrito;
        this.id_producto = id_producto;
        this.id_vendedor = id_vendedor;
        this.cantidad = cantidad;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getCantidad() {
        return cantidad;
    }

public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
