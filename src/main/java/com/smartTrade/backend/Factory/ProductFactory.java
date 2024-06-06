package com.smartTrade.backend.Factory;

import com.smartTrade.backend.Models.*;

public class ProductFactory {

    public static Producto getProduct(String nombre, String descripcion,
                                      int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, int id_imagen, int stock, String etiqueta_inteligente) {

        Product_Types productType;
        switch (id_categoria) {
            case 1:
                productType = Product_Types.ALIMENTACION;
                break;
            case 2:
                productType = Product_Types.MODA;
                break;
            case 3:
                productType = Product_Types.ELECTRONICA;
                break;
            case 4:
                productType = Product_Types.HIGIENE;
                break;
            case 5:
                productType = Product_Types.FRESCOS;
                break;
            case 6:
                productType = Product_Types.PROCESADOS;
                break;
            case 7:
                productType = Product_Types.DEPORTE;
                break;
            case 8:
                productType = Product_Types.COMIDA;
                break;
            case 9:
                productType = Product_Types.BEBIDA;
                break;
            default:
                productType = null;
                break;
        }
        return getProduct(productType, nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica, id_imagen, stock, etiqueta_inteligente);
    }

    private static Producto getProduct(Product_Types productType, String nombre, String descripcion,
                                       int id_categoria, java.sql.Date fecha_publicacion, boolean validado, int huella_ecologica, int id_imagen, int stock, String etiqueta_inteligente) {
        if (productType == null) {
            return null;
        }
        Producto producto = new Producto(nombre, descripcion, id_categoria, fecha_publicacion, validado, huella_ecologica, id_imagen, stock, etiqueta_inteligente);
        AlimentacionFactory alimentacionFactory = new AlimentacionFactory();
        HigieneFactory higieneFactory = new HigieneFactory();
        switch (productType) {
            case HIGIENE:
                return higieneFactory.getProduct(producto);
            case ALIMENTACION:
                return alimentacionFactory.getProduct(productType, producto);
            case COMIDA:
                return alimentacionFactory.getProduct(productType, producto);
            case BEBIDA:
                return alimentacionFactory.getProduct(productType, producto);
            case DEPORTE:
                return new DeporteFactory().getProduct(producto);
            case ELECTRONICA:
                return new ElectronicaFactory().getProduct(producto);
            case FRESCOS:
                return alimentacionFactory.getProduct(productType, producto);
            case MODA:
                return new ModaFactory().getProduct(producto);
            case PROCESADOS:
                return alimentacionFactory.getProduct(productType, producto);
            default:
                break;
        }

        return null;
    }

    static class HigieneFactory extends ProductFactory {

        public Higiene getProduct(Producto producto) {
            return new Higiene(producto.getNombre(), producto.getDescripcion(), producto.getId_categoria(), producto.getFecha_publicacion(), producto.getValidado(), producto.getHuella_ecologica(), producto.getId_imagen(), producto.getStock(), producto.getEtiqueta_inteligente());
        }
    }

    static class AlimentacionFactory extends ProductFactory {

        public Alimentacion getProduct(Product_Types type, Producto p) {
            if (type == Product_Types.PROCESADOS || type == Product_Types.FRESCOS || type == Product_Types.COMIDA) {
                ComidaFactory comidaFactory = new ComidaFactory();

                switch (type) {
                    case PROCESADOS:
                        return comidaFactory.getProduct(type, p);
                    case FRESCOS:
                        return comidaFactory.getProduct(type, p);
                    case COMIDA:
                        return comidaFactory.getProduct(type, p);
                    default:
                        return null;
                }
            } else if (type == Product_Types.BEBIDA) {
                return new BebidaFactory().getProduct(p);
            } else {
                return new Alimentacion(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente());
            }

        }

        static class ComidaFactory extends AlimentacionFactory {

            public Comida getProduct(Product_Types tipo, Producto p) {
                switch (tipo) {
                    case FRESCOS:
                        return new FrescosFactory().getProduct(p);
                    case PROCESADOS:
                        return new ProcesadosFactory().getProduct(p);
                    case COMIDA:
                        return new Comida(p.getNombre(), p.getDescripcion(),
                                p.getId_categoria(), p.getFecha_publicacion(),
                                p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente());
                    default:
                        return null;
                }
            }
        }

        static class ProcesadosFactory extends ComidaFactory {

            public Procesados getProduct(Producto p) {
                return new Procesados(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente(), null, 0.0);
            }
        }

        static class FrescosFactory extends ComidaFactory {

            public Frescos getProduct(Producto p) {
                return new Frescos(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente(), null, 0.0);
            }
        }

        static class BebidaFactory extends AlimentacionFactory {

            public Bebida getProduct(Producto p) {
                return new Bebida(p.getNombre(), p.getDescripcion(),
                        p.getId_categoria(), p.getFecha_publicacion(),
                        p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente());
            }
        }
    }


    static class DeporteFactory extends ProductFactory {

        public Deporte getProduct(Producto p) {
            return new Deporte(p.getNombre(), p.getDescripcion(),
                    p.getId_categoria(), p.getFecha_publicacion(),
                    p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente(), null, null, null, null);
        }
    }

    static class ElectronicaFactory extends ProductFactory {

        public Electronica getProduct(Producto p) {
            return new Electronica(p.getNombre(), p.getDescripcion(),
                    p.getId_categoria(), p.getFecha_publicacion(),
                    p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente(), null, null, null, null);
        }
    }

    static class ModaFactory extends ProductFactory {

        public Moda getProduct(Producto p) {
            return new Moda(p.getNombre(), p.getDescripcion(),
                    p.getId_categoria(), p.getFecha_publicacion(),
                    p.getValidado(), p.getHuella_ecologica(), p.getId_imagen(), p.getStock(), p.getEtiqueta_inteligente(), null, null, null, null, null);
        }
    }
}
