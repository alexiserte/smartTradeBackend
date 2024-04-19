package com.smartTrade.backend.models;

public class Producto{

    private String nombre;
    private String descripcion;
    private int id_categoria;
    private String imagen;
    private java.sql.Date fecha_publicacion;
    private boolean validado;
    private int huella_ecologica;
    public Producto(String nombre, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
        this.imagen = imagen;
        this.fecha_publicacion = fecha_publicacion;
        this.validado = validado;
        this.huella_ecologica = huella_ecologica;
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

    public String getImagen() {
        return imagen;
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

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
}