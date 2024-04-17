package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.*;

public class ProductFactory{

    public static Producto getProduct(Product_Types productType, String nombre, int id_vendedor, double precio, String descripcion,
            int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
            Object... args) {
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
                if (args.length >= 4) {
                    return new Deporte(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                            validado, huella_ecologica, (String) args[0], (String) args[1], (String) args[2], (String) args[3]);
                }
                break;
            case ELECTRONICA:
                if (args.length >= 4) {
                    return new Electronica(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                            validado, huella_ecologica, (String) args[0], (String) args[1], (String) args[2], (String) args[3]);
                }
                break;
            case FRESCOS:
                if (args.length >= 2) {
                    return new Frescos(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                            validado, huella_ecologica, (String) args[1], (double) args[0]);
                }
                break;
            case MODA:
                if (args.length >= 5) {
                    return new Moda(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado,
                            huella_ecologica, (String) args[0], (String) args[1], (String) args[2], (String) args[3], (String) args[4]);
                }
                break;
            case PROCESADOS:
                if (args.length >= 2) {
                    return new Procesados(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                            validado, huella_ecologica, (String) args[0], (double) args[1]);
                }
                break;
            default:
                break;
        }

        return null;
    }

}
