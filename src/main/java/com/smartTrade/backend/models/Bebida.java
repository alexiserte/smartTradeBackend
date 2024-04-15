package com.smartTrade.backend.models;

public class Bebida extends Alimentacion{

    public Bebida(String nombre, int id_vendedor, double precio, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        super(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
    }

    public Bebida() {
        super();
    }
    
}
