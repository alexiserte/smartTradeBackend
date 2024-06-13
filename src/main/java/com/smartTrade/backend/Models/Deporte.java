package com.smartTrade.backend.Models;

public class Deporte extends Producto {
    private String tipo;
    private String marca;
    private String modelo;
    private String especificaciones;

    public Deporte(String nombre, String descripcion, int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, int id_imagen, int stock, String etiqueta_inteligente, String tipo, String marca, String modelo, String especificaciones) {
        super(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica, id_imagen, stock, etiqueta_inteligente);
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.especificaciones = especificaciones;
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
