package com.smartTrade.backend.Models;

public class Comida extends Alimentacion {

    public Comida(String nombre, String descripcion, int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, int id_imagen, int stock, String etiqueta_inteligente) {
        super(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica, id_imagen, stock, etiqueta_inteligente);
    }

    public Comida() {
        super();
    }

}
