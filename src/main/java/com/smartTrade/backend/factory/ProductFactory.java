package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.*;

import java.util.List;

public class ProductFactory{

    public static Producto getProduct(Product_Types productType, String nombre, String descripcion,
    int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica,int id_imagen, List<Object> args) {
        if (productType == null) {
            return null;
        }
        Producto producto = new Producto(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica,id_imagen);
        AlimentacionFactory alimentacionFactory = new AlimentacionFactory();
        HigieneFactory higieneFactory = new HigieneFactory();
        switch (productType) {
            case HIGIENE:
                return higieneFactory.getProduct(producto, args);    
            case ALIMENTACION:
                return alimentacionFactory.getProduct(productType,producto, args);    
            case COMIDA:
                return alimentacionFactory.getProduct(productType,producto, args);
            case BEBIDA:
                return alimentacionFactory.getProduct(productType, producto, args);
            case DEPORTE:
                if (args.size() >= 4) {
                    DeporteFactory deporteFactory = new DeporteFactory();
                    return deporteFactory.getProduct(producto, args);
                }
                break;
            case ELECTRONICA:
                if (args.size() >= 4) {
                    ElectronicaFactory electronicaFactory = new ElectronicaFactory();
                    return electronicaFactory.getProduct(producto, args);   
                }
                break;
            case FRESCOS:
                if (args.size() >= 2) {
                    return alimentacionFactory.getProduct(productType,producto, args);    
                }
                break;
            case MODA:
                if (args.size() >= 5) {
                    ModaFactory modaFactory = new ModaFactory();
                    return modaFactory.getProduct(producto, args); 
                }
                break;
            case PROCESADOS:
                if (args.size() >= 2) {
                    return alimentacionFactory.getProduct(productType,producto, args);
                }
                break;
            default:
                break;
        }

        return null;
    }

     static class HigieneFactory extends ProductFactory {
        
        public  Higiene getProduct(Producto producto, List<Object> args) {
            return new Higiene(producto.getNombre(), producto.getDescripcion(), producto.getId_categoria(), producto.getFecha_publicacion(), producto.getValidado(), producto.getHuella_ecologica(),producto.getId_imagen());
        }
    }

     static class AlimentacionFactory extends ProductFactory {

        public  Alimentacion getProduct(Product_Types type, Producto p, List<Object> args) {
            BebidaFactory bebidaFactory = new BebidaFactory();
            if (type == Product_Types.PROCESADOS || type == Product_Types.FRESCOS || type == Product_Types.COMIDA) {
                ComidaFactory comidaFactory = new ComidaFactory();
                
                switch (type) {
                    case PROCESADOS:
                        return comidaFactory.getProduct(type, p, args);
                    case FRESCOS:
                        return comidaFactory.getProduct(type,p, args);
                    case COMIDA:
                        return comidaFactory.getProduct(type, p, args);
                    default:
                        return null;
                }
            } else if(type == Product_Types.BEBIDA) {
                return bebidaFactory.getProduct(p, args);
            }
            else{
                return new Alimentacion(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), p.getId_imagen());
            }

        }

        static class ComidaFactory extends AlimentacionFactory {

            public  Comida getProduct(Product_Types tipo, Producto p,
                    List<Object> args) {
                ProcesadosFactory procesadosFactory = new ProcesadosFactory();
                FrescosFactory frescosFactory = new FrescosFactory();
                switch (tipo) {
                    case FRESCOS:
                        return frescosFactory.getProduct(p, args);
                    case PROCESADOS:
                        return procesadosFactory
                        .getProduct(p, args);
                    case COMIDA:
                        return new Comida(p.getNombre(), p.getDescripcion(),
                                p.getId_categoria(), p.getFecha_publicacion(),
                                p.getValidado(), p.getHuella_ecologica(), p.getId_imagen());
                    default:
                        return null;
                }
            }
        }

        static class ProcesadosFactory extends ComidaFactory {

            public  Procesados getProduct(Producto p,
                    List<Object> args) {
                return new Procesados(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(),  p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(),p.getId_imagen(), (String) args.get(0), (double) args.get(1));
            }
        }

        static class FrescosFactory extends ComidaFactory {

            public  Frescos getProduct(Producto p,
                    List<Object> args) {
                return new Frescos(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(),p.getId_imagen(), (String) args.get(0), (double) args.get(1));
            }
        }

        static class BebidaFactory extends AlimentacionFactory {

            public  Bebida getProduct(Producto p,
                    List<Object> args) {
                return new Bebida(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(),p.getId_imagen());
            }
        }
    }


     static class DeporteFactory extends ProductFactory {
        
        public  Deporte getProduct(Producto p,
                List<Object> args) {
            return new Deporte(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(),p.getId_imagen(), (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3));
        }
    }

    static  class ElectronicaFactory extends ProductFactory {
        
        public  Electronica getProduct(Producto p,
                List<Object> args) {
            return new Electronica(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(),(String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3));
        }
    }


     static class ModaFactory extends ProductFactory {
        
        public  Moda getProduct(Producto p,
                List<Object> args) {
            return new Moda(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(),p.getId_imagen(), (String) args.get(0), (String) args.get(1), (String) args.get(2), (String) args.get(3), (String) args.get(4));
        }
    }

}
