package com.smartTrade.backend.Models;

public class ProductoListaDeseos {
    private int id_listaDeseos;
    private int id_producto;
    private int id_vendedor;

    public ProductoListaDeseos(int id_listaDeseos, int id_producto, int id_vendedor) {
        this.id_listaDeseos = id_listaDeseos;
        this.id_producto = id_producto;
        this.id_vendedor = id_vendedor;
    }

    public ProductoListaDeseos() {
    }

    public int getId_listaDeseos() {
        return id_listaDeseos;
    }

    public void setId_listaDeseos(int id_carrito) {
        this.id_listaDeseos = id_listaDeseos;
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


}
