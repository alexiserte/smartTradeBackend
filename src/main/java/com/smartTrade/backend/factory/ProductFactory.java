package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.*;

public class ProductFactory{

    public Producto getProduct(String productType, String nombre, int id_vendedor, double precio, String descripcion,
            int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
            Object... args) {
        if (productType == null) {
            return null;
        }
        if (productType.equalsIgnoreCase("HIGIENE")) {
            return new Higiene(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
        } else if (productType.equalsIgnoreCase("ALIMENTACION")) {
            return new Alimentacion(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
        } else if (productType.equalsIgnoreCase("COMIDA")) {
            return new Comida(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
        } else if (productType.equalsIgnoreCase("BEBIDA")) {
            return new Bebida(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
        } else if (productType.equalsIgnoreCase("DEPORTE")) {
            String tipo = "";
            String marca = "";
            String modelo = "";
            String especificaciones = "";
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    tipo = (String) args[i];
                } else if (i == 1) {
                    marca = (String) args[i];
                } else if (i == 2) {
                    modelo = (String) args[i];
                } else if (i == 3) {
                    especificaciones = (String) args[i];
                }
            }
            return new Deporte(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, tipo, marca, modelo, especificaciones);
        } else if (productType.equalsIgnoreCase("ELECTRÓNICA")) {
            String tipo = "";
            String marca = "";
            String modelo = "";
            String especificaciones_tecnicas = "";
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    tipo = (String) args[i];
                } else if (i == 1) {
                    marca = (String) args[i];
                } else if (i == 2) {
                    modelo = (String) args[i];
                } else if (i == 3) {
                    especificaciones_tecnicas = (String) args[i];
                }
            }
            return new Electronica(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, tipo, marca, modelo, especificaciones_tecnicas);
        } else if (productType.equalsIgnoreCase("FRESCOS")) {
            double peso = 0;
            String origen = "";
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    peso = (double) args[i];
                } else if (i == 1) {
                    origen = (String) args[i];
                }
            }

            return new Frescos(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, origen, peso);
        } else if (productType.equalsIgnoreCase("MODA")) {
            String talla = "";
            String marca = "";
            String color = "";
            String tipoDePrenda = "";
            String seccion = "";

            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    talla = (String) args[i];
                } else if (i == 1) {
                    marca = (String) args[i];
                } else if (i == 2) {
                    color = (String) args[i];
                } else if (i == 3) {
                    tipoDePrenda = (String) args[i];
                } else if (i == 4) {
                    seccion = (String) args[i];
                }
            }
            return new Moda(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado,
                    huella_ecologica, talla, marca, color, tipoDePrenda, seccion);
        } else if (productType.equalsIgnoreCase("PROCESADOS")) {
            String ingredientes = "";
            double peso = 0;
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    ingredientes = (String) args[i];
                } else if (i == 1) {
                    peso = (double) args[i];
                }
            }
            return new Procesados(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, ingredientes, peso);
        }

        return null;
    }
}
