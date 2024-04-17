package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.*;

public class ProductFactory{

    public static Producto getProduct(Product_Types productType, String nombre, int id_vendedor, double precio, String descripcion,
            int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica) {
        if (productType == null) {
            return null;
        }

        switch (productType) {
            case HIGIENE:
                return new Higiene(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case ALIMENTACION:
                return new Alimentacion(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case COMIDA:
                return new Comida(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case BEBIDA:
                return new Bebida(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case DEPORTE:
                return new Deporte(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case ELECTRONICA:
                return new Electronica(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case FRESCOS:
                return new Frescos(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case PROCESADOS:
                return new Procesados(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            case MODA:
                return new Moda(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
            default:
                break;
        }
        return null;
    }

}
