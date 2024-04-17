package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.*;

import java.util.List;

public class ProductFactory{

    public static Producto getProduct(Product_Types productType,Producto producto, List<Object> args) {
        if (productType == null) {
            return null;
        }
        
        switch (productType) {
            case HIGIENE:
                return HigieneFactory.getProduct(producto, args);    
            case ALIMENTACION:
                return AlimentacionFactory.getProduct(producto, args);    
            case COMIDA:
                return ComidaFactory.getProduct(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica, args);
            case BEBIDA:
                return BebidaFactory.getProduct(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica, args);
            case DEPORTE:
                if (args.size() >= 4) {
                    return DeporteFactory.getProduct(productType, nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica, args);
                }
                break;
            case ELECTRONICA:
                if (args.size() >= 4) {
                    return ElectronicaFactory.getProduct(productType, nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica, args);   
                }
                break;
            case FRESCOS:
                if (args.size() >= 2) {
                    return FrescosFactory.getProduct(productType, nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica, args);    
                }
                break;
            case MODA:
                if (args.size() >= 5) {
                    return ModaFactory.getProduct(productType, nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica, args); 
                }
                break;
            case PROCESADOS:
                if (args.size() >= 2) {
                    ;
                }
                break;
            default:
                break;
        }

        return null;
    }

    class HigieneFactory extends ProductFactory {
        
        public static Higiene getProduct(Producto producto, List<Object> args) {
            return new Higiene(producto.getNombre(), producto.getId_vendedor(), producto.getPrecio(), producto.getDescripcion(), producto.getId_categoria(), producto.getImagen(), producto.getFecha_publicacion(), producto.getValidado(), producto.getHuella_ecologica());
        }
    }

    class AlimentacionFactory extends ProductFactory {
        
       public static Alimentacion getProduct(Producto p, List<Object> args) {
            return new Alimentacion(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(), p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(), p.getValidado(), p.getHuella_ecologica());
        }



        class ProcesadosFactory extends AlimentacionFactory {
        
            public static Procesados getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                    int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                    List<Object> args) {
                return new Procesados(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                        validado, huella_ecologica, (String) args.get(0), (double) args.get(1));
            }
        }
    }

    class ComidaFactory extends ProductFactory {
        
        public static Comida getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                List<Object> args) {
            return new Comida(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
        }
    }

    class BebidaFactory extends ProductFactory {
        
        public static Bebida getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                List<Object> args) {
            return new Bebida(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica);
        }
    }

    class DeporteFactory extends ProductFactory {
        
        public static Deporte getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                List<Object> args) {
            return new Deporte(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3));
        }
    }

    class ElectronicaFactory extends ProductFactory {
        
        public static Electronica getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                List<Object> args) {
            return new Electronica(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3));
        }
    }

    class FrescosFactory extends ProductFactory {
        
        public static Frescos getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                List<Object> args) {
            return new Frescos(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, (String) args.get(0), (double) args.get(1));
        }
    }

    class ModaFactory extends ProductFactory {
        
        public static Moda getProduct(String nombre, int id_vendedor, double precio, String descripcion,
                int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,
                List<Object> args) {
            return new Moda(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion,
                    validado, huella_ecologica, (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3), (String) args.get(4));
        }
    }

}
