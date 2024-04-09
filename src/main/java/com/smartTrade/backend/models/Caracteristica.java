package com.smartTrade.backend.models;

public class Caracteristica {
    private String nombre;
    private int id_producto;
    private String valor;
    private int id_categoria;
    
    public Caracteristica(String nombre, int id_producto, String valor, int id_categoria) {
        this.nombre = nombre;
        this.id_producto = id_producto;
        this.valor = valor;
        this.id_categoria = id_categoria;
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

    public int getId_categoria() {
        return this.id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
}
