package com.smartTrade.backend.models;

public class Electronica extends Producto{
    private String marca;
    private String modelo;
    private String tipo;
    private String especificacionesTecnicas;

    public Electronica(String nombre, String descripcion, int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,int id_imagen, String marca, String modelo, String tipo, String especificacionesTecnicas) {
        super(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica,id_imagen);
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.especificacionesTecnicas = especificacionesTecnicas;
    }

    public Electronica() {
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEspecificacionesTecnicas() {
        return especificacionesTecnicas;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEspecificacionesTecnicas(String especificacionesTecnicas) {
        this.especificacionesTecnicas = especificacionesTecnicas;
    }
}

