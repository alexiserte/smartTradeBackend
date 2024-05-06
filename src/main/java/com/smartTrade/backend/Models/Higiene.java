package com.smartTrade.backend.Models;

public class Higiene extends Producto{

    public Higiene(String nombre, String descripcion, int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, int id_imagen, int stock) {
        super(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica,id_imagen,stock);
    }

    public Higiene() {
        super();
    }
}
