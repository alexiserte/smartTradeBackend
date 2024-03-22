package com.smartTrade.backend.models;

public class Retos {
    private int id_reto;
    private String recompensa;

    public Retos(int id_reto, String recompensa) {
        this.id_reto = id_reto;
        this.recompensa = recompensa;
    }

    public int getId_reto() {
        return id_reto;
    }

    public void setId_reto(int id_reto) {
        this.id_reto = id_reto;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }
}
