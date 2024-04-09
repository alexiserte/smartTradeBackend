package com.smartTrade.backend.models;

public class Categoria {
    private String nombre;
    private Categoria categoria_principal;

    public Categoria(String nombre, Categoria categoria_principal) {
        this.nombre = nombre;
        this.categoria_principal = categoria_principal;
    }


    public Categoria(String nombre){
        this.nombre = nombre;
        this.categoria_principal = null;
    }

    public Categoria(){}

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria_principal() {
        return categoria_principal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria_principal(Categoria categoria_principal) {
        this.categoria_principal = categoria_principal;
    }
}
