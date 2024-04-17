package com.smartTrade.backend.models;

public class Procesados extends Comida{
    private String ingredientes;
    private Double peso;

    public Procesados(String nombre, int id_vendedor, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        super(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
        this.ingredientes = null;
        this.peso = null;
    }

    public Procesados() {
        super();
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public double getPeso() {
        return peso;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

}
