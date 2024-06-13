package com.smartTrade.backend.Models;

public class Categoria {
    private String nombre;
    private Integer categoria_principal;

    public Categoria(String nombre, int categoria_principal) {
        this.nombre = nombre;
        this.categoria_principal = categoria_principal;
    }


    public Categoria(String nombre) {
        this.nombre = nombre;
        this.categoria_principal = null;
    }

    public Categoria() {
    }

    public String getNombre() {
        return nombre;
    }

    public int getCategoria_principal() {
        return categoria_principal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria_principal(int categoria_principal) {
        this.categoria_principal = categoria_principal;
    }
}
