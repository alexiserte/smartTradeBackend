package com.smartTrade.backend.models;

public class Producto{
    private String nombre;
    private int id_producto;
    private int id_vendedor;
    private double precio;
    private String descripcion;
    private int id_categoria;

    public Producto(String nombre, int id_categoria, int id_vendedor, double precio, String descripcion) {
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.id_vendedor = id_vendedor;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Producto() {}

    public int getId_producto() {
        return this.id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_vendedor() {
        return this.id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }



    public String getDescripcion() {
        return this.descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_categoria() {
        return this.id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }


}