package com.smartTrade.backend.models;

public class Producto{
    private String nombre;
    private int id_vendedor;
    private double precio;
    private String descripcion;
    private int id_categoria;
    private String imagen;
    private java.sql.Date fecha_publicacion;

    public Producto(String nombre, int id_vendedor, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion) {
        this.nombre = nombre;
        this.id_vendedor = id_vendedor;
        this.precio = precio;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
        this.imagen = imagen;
        this.fecha_publicacion = fecha_publicacion;
    }

    public Producto(){}

    public String getNombre() {
        return nombre;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public double getPrecio() {
        return precio;
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

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
}