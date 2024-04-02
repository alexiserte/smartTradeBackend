package com.smartTrade.backend.models;

public class Caracteristica {
    private String nombre;
    private int id_producto;
    private String valor;
    
    public Caracteristica(String nombre, int id_producto, String valor) {
        this.nombre = nombre;
        this.id_producto = id_producto;
        this.valor = valor;
    }

    public Caracteristica(){}

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_producto() {
        return this.id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
