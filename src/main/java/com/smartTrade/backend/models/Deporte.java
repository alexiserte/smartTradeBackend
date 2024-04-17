package com.smartTrade.backend.models;

public class Deporte extends Producto{
    private String tipo;
    private String marca;
    private String modelo;
    private String especificaciones;

    public Deporte(String nombre, int id_vendedor, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        super(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
        this.tipo = null;
        this.marca = null;
        this.modelo = null;
        this.especificaciones = null;
    }

    public Deporte() {
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }
}
