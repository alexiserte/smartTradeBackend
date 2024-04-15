package com.smartTrade.backend.models;

public class Moda extends Producto {
    private String talla;
    private String  marca;
    private String color;
    private String tipoDePrenda;
    private String seccion;

    public Moda(String nombre, int id_vendedor, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, String talla, String marca, String color, String tipoDePrenda, String seccion) {
        super(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
        this.talla = talla;
        this.marca = marca;
        this.color = color;
        this.tipoDePrenda = tipoDePrenda;
        this.seccion = seccion;
    }

    public Moda() {
    }

    public String getTalla() {
        return talla;
    }

    public String getMarca() {
        return marca;
    }

    public String getColor() {
        return color;
    }

    public String getTipoDePrenda() {
        return tipoDePrenda;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTipoDePrenda(String tipoDePrenda) {
        this.tipoDePrenda = tipoDePrenda;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

}
