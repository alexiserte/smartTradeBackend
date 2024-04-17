package com.smartTrade.backend.models;

public class Frescos extends Comida{

    private String origen;
    private Double peso;

    public Frescos(String nombre, int id_vendedor, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        super(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
        this.origen = null;
        this.peso = null;
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
