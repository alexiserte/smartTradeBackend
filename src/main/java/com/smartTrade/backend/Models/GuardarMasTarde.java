package com.smartTrade.backend.Models;

public class GuardarMasTarde {
    private int id_guardar_mas_tarde;
    private int id_comprador;

    public GuardarMasTarde(int id_guardar_mas_tarde, int id_comprador) {
        this.id_guardar_mas_tarde = id_guardar_mas_tarde;
        this.id_comprador = id_comprador;
    }

    public int getId_guardar_mas_tarde() {
        return id_guardar_mas_tarde;
    }

    public void setId_guardar_mas_tarde(int id_guardar_mas_tarde) {
        this.id_guardar_mas_tarde = id_guardar_mas_tarde;
    }

    public int getId_comprador() {
        return id_comprador;
    }

    public void setId_comprador(int id_comprador) {
        this.id_comprador = id_comprador;
    }
}
