package com.smartTrade.backend.models;

public class Alimentacion extends Producto{
    
    public Alimentacion(String nombre, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        super(nombre, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
    }

    public Alimentacion() {
        super();
    }
}
