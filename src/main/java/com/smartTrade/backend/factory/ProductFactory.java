package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.*;

import java.util.List;

public class ProductFactory{

    public static Producto getProduct(Product_Types productType, String nombre, int id_vendedor, double precio, String descripcion,
    int id_categoria, String imagen, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, List<Object> args) {
        if (productType == null) {
            return null;
        }
        Producto producto = new Producto(nombre, id_vendedor, precio, descripcion, id_categoria, imagen, fecha_publicacion, validado, huella_ecologica);
        
        switch (productType) {
            case HIGIENE:
                return HigieneFactory.getProduct(producto, args);    
            case ALIMENTACION:
                return AlimentacionFactory.getProduct(productType,producto, args);    
            case COMIDA:
                return AlimentacionFactory.getProduct(productType,producto, args);
            case BEBIDA:
                return AlimentacionFactory.getProduct(productType, producto, args);
            case DEPORTE:
                if (args.size() >= 4) {
                    return DeporteFactory.getProduct(productType, producto, args);
                }
                break;
            case ELECTRONICA:
                if (args.size() >= 4) {
                    return ElectronicaFactory.getProduct(productType, producto, args);   
                }
                break;
            case FRESCOS:
                if (args.size() >= 2) {
                    return AlimentacionFactory.getProduct(productType,producto, args);    
                }
                break;
            case MODA:
                if (args.size() >= 5) {
                    return ModaFactory.getProduct(productType, producto, args); 
                }
                break;
            case PROCESADOS:
                if (args.size() >= 2) {
                    return AlimentacionFactory.getProduct(productType,producto, args);
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

        public static Alimentacion getProduct(Product_Types type, Producto p, List<Object> args) {
            if (type == Product_Types.PROCESADOS || type == Product_Types.FRESCOS) {
                switch (type) {
                    case PROCESADOS:
                        return ComidaFactory.getProduct(type, p, args);
                    case FRESCOS:
                        return FrescosFactory.getProduct(p, args);
                    case ALIMENTACION:
                        return new Alimentacion(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                                p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                                p.getValidado(), p.getHuella_ecologica());
                    default:
                        return null;
                }
            } else {
                return BebidaFactory.getProduct(p, args);
            }

        }

        class ComidaFactory extends AlimentacionFactory {

            public static Comida getProduct(Product_Types tipo, Producto p,
                    List<Object> args) {
                switch (tipo) {
                    case FRESCOS:
                        return FrescosFactory.getProduct(p, args);
                    case PROCESADOS:
                        return ProcesadosFactory.getProduct(p, args);
                    case COMIDA:
                        return new Comida(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                                p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                                p.getValidado(), p.getHuella_ecologica());
                    default:
                        return null;
                }
            }
        }

        class ProcesadosFactory extends ComidaFactory {

            public static Procesados getProduct(Producto p,
                    List<Object> args) {
                return new Procesados(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                        p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), (String) args.get(0), (double) args.get(1));
            }
        }

        class FrescosFactory extends ComidaFactory {

            public static Frescos getProduct(Producto p,
                    List<Object> args) {
                return new Frescos(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                        p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), (String) args.get(0), (double) args.get(1));
            }
        }

        class BebidaFactory extends AlimentacionFactory {

            public static Bebida getProduct(Producto p,
                    List<Object> args) {
                return new Bebida(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                        p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica());
            }
        }
    }


    class DeporteFactory extends ProductFactory {
        
        public static Deporte getProduct(Producto p,
                List<Object> args) {
            return new Deporte(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                        p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3));
        }
    }

    class ElectronicaFactory extends ProductFactory {
        
        public static Electronica getProduct(Producto p,
                List<Object> args) {
            return new Electronica(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                        p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3));
        }
    }


    class ModaFactory extends ProductFactory {
        
        public static Moda getProduct(Producto p
                List<Object> args) {
            return new Moda(p.getNombre(), p.getId_vendedor(), p.getPrecio(), p.getDescripcion(),
                        p.getId_categoria(), p.getImagen(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3), (String) args.get(4));
        }
    }

}
