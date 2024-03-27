package com.smartTrade.backend.models;

public class Vendedor {
    private int id_vendedor;
    private String nombre_vendedor;

    public Vendedor(int id_vendedor, String nombre_vendedor) {
        this.id_vendedor = id_vendedor;
        this.nombre_vendedor = nombre_vendedor;
    }

    public Vendedor(){}

    public int getId_vendedor() {
        return this.id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getNombre_vendedor() {
        return this.nombre_vendedor;
    }

    public void setNombre_vendedor(String nombre_vendedor) {
        this.nombre_vendedor = nombre_vendedor;
    }
}
