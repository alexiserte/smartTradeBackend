package com.smartTrade.backend.Models;

public class ListaDeDeseos {
    private int id_lista_de_datos;
    private int id_comprador;

    public ListaDeDeseos(int id_lista_de_datos, int id_comprador) {
        this.id_lista_de_datos = id_lista_de_datos;
        this.id_comprador = id_comprador;
    }

    public int getId_lista_de_datos() {
        return id_lista_de_datos;
    }

    public void setId_lista_de_datos(int id_lista_de_datos) {
        this.id_lista_de_datos = id_lista_de_datos;
    }

    public int getId_comprador() {
        return id_comprador;
    }

    public void setId_comprador(int id_comprador) {
        this.id_comprador = id_comprador;
    }
}
