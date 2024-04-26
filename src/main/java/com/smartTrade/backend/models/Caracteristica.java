package com.smartTrade.backend.Models;

public class Caracteristica {
    private int id_caracteristica;
    private int id_producto;
    private String valor;
    private int id_categoria;
    
    public Caracteristica(int id_caracteristica, int id_producto, String valor, int id_categoria) {
        this.id_caracteristica = id_caracteristica;
        this.id_producto = id_producto;
        this.valor = valor;
        this.id_categoria = id_categoria;
    }

    public Caracteristica(){}

    public int getId_caracteristica() {
        return id_caracteristica;
    }

    public int getId_producto() {
        return id_producto;
    }

    public String getValor() {
        return valor;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_caracteristica(int id_caracteristica) {
        this.id_caracteristica = id_caracteristica;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

}
