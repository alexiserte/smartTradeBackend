package com.smartTrade.backend.models;

public class Producto{

    private String nombre;
    private String descripcion;
    private int id_categoria;
    private java.sql.Date fecha_publicacion;
    private boolean validado;
    private int huella_ecologica;
    private int id_imagen;
    public Producto(String nombre, String descripcion, int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, int id_imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
        this.fecha_publicacion = fecha_publicacion;
        this.validado = validado;
        this.huella_ecologica = huella_ecologica;
        this.id_imagen = id_imagen;
    }

    public Producto(){}

    public String getNombre() {
        return nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public int getId_categoria() {
        return id_categoria;
    }



    public java.sql.Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }




    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }


    public void setFecha_publicacion(java.sql.Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public boolean getValidado() {
        return this.validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public int getHuella_ecologica() {
        return huella_ecologica;
    }

    public void setHuella_ecologica(int huella_ecologica) {
        this.huella_ecologica = huella_ecologica;
    }

    public int getId_imagen() {
        return id_imagen;
    }

    public void setId_imagen(int id_imagen) {
        this.id_imagen = id_imagen;
    }
}