package com.smartTrade.backend.Models;

public class Consejo {
    private int consejo_id;
    private int id_producto;

    public Consejo(int consejo_id, int id_producto) {
        this.consejo_id = consejo_id;
        this.id_producto = id_producto;
    }

    public int getConsejo_id() {
        return consejo_id;
    }

    public void setConsejo_id(int consejo_id) {
        this.consejo_id = consejo_id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
}
