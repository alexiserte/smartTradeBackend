package com.smartTrade.backend.models;

public class Categoria_Caracteristica {
    private String nombre;
    private int id_categoria;;

    public Categoria_Caracteristica(String nombre, int id_categoria) {
        this.nombre = nombre;
        this.id_categoria = id_categoria;
    }

    public Categoria_Caracteristica(){}

    public String getNombre() {
        return nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
}
