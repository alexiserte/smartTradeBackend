package com.smartTrade.backend.models;

public class Producto{
    private int id_producto;
    private int id_vendedor;
    private double precio;
    private String material;
    private String descripcion;

    public Producto(int id_producto, int id_vendedor, double precio, String material, String descripcion) {
        this.id_producto = id_producto;
        this.id_vendedor = id_vendedor;
        this.precio = precio;
        this.material = material;
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

    public String getMaterial() {
        return this.material;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}