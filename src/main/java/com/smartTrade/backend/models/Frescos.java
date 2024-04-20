package com.smartTrade.backend.models;

public class Frescos extends Comida{

    private String origen;
    private double peso;

    public Frescos(String nombre, String descripcion, int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,int id_imagen, String origen, double peso) {
        super(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica,id_imagen);
        this.origen = origen;
        this.peso = peso;
    }

    public Frescos() {
        super();
    }

    public String getOrigen() {
        return origen;
    }

    public double getPeso() {
        return peso;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
}
