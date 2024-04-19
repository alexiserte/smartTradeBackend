package com.smartTrade.backend.models;

public class Comida extends Alimentacion{

    public Comida(String nombre, String descripcion, int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        super(nombre, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
    }

    public Comida() {
        super();
    }
    
}
