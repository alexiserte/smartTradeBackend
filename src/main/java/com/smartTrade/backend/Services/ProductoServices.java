package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.*;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Utils.QRGenerator;
import com.smartTrade.backend.Utils.SmartTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductoServices{

    @Autowired
    private CategoriaServices categoriaServices;

    @Autowired
    private VendedorServices vendedorServices;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private ImagenDAO imagenDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PrecioDAO precioDAO;

    @Autowired
    private SmartTagDAO smartTagDAO;

    public void createNewProduct(String nombre,String characteristicName,String vendorName,double precio,String descripcion,String imagen){
        productoDAO.create(nombre,characteristicName,vendorName,precio,descripcion,imagen);
    }

    public Object readFullProduct(String nombre){
        return productoDAO.readOne(nombre);
    }

    public Producto readOneProduct(String nombre){
        return productoDAO.readOneProduct(nombre);
    }

    public ProductoDAO.ProductoAntiguo readOneProductOld(String nombre){
        return productoDAO.readOneProductAntiguo(nombre);
    }

    public List<Producto> readAllProducts(){
        return productoDAO.readAll();
    }

    public List<String> readAllProductsNames(){
        List<String> names = new ArrayList<>();
        for(Producto p : readAllProducts()){
            names.add(p.getNombre());
        }
        return names;
    }

    public void updateProduct(String nombre, Map atributos){
        productoDAO.update(nombre, atributos);
    }

    public void deleteProduct(String nombre){
        productoDAO.delete(nombre);
    }


    public List<Producto> getProductosFromOneCategoria(String categoria){
        int id_categoria = categoriaServices.getIDFromName(categoria);
        List<Producto> result = new ArrayList<>();
        for(Producto p : readAllProducts()){
            if(p.getId_categoria() == id_categoria){
                result.add(p);
            }
        }
        return result;
    }

    public boolean isFromOneCategoria(String nombre, String categoria){
        int id_categoria = categoriaServices.getIDFromName(categoria);
        Producto p = readOneProduct(nombre);
        return p.getId_categoria() == id_categoria;
    }

    public void validarProducto(String nombre){
        productoDAO.validate(nombre);
    }

    public List<Producto> readProductosPendientesDeValidacion(){
        List<Producto> result = new ArrayList<>();
        for(Producto p : readAllProducts()){
            if(!p.getValidado()) result.add(p);
        }
        return result;
    }

    public void deleteProductoFromOneVendor(String productName, String vendorName){
        productoDAO.deleteProduct(productName, vendorName);
    }

    public void updateProductoFromOneVendor(String productName, String vendorName, Map atributos){
        productoDAO.updateProductFromOneVendor(productName, vendorName, atributos);
    }

    public String getImageFromOneProduct(String productName){
        int id =  productoDAO.readOneProduct(productName).getId_imagen();
        return imagenDAO.readOne(id);
    }

    public List<Producto> getProductosCompradosPorUsuario(String username){
        return productoDAO.getProductosComprados(username);
    }

    public HashMap<Producto,String> getImagesFromAllProducts(){
        List<String> nombresDeProductos = readAllProductsNames();
        HashMap<Producto,String> result = new HashMap<>();
        for(String nombre : nombresDeProductos){
            result.put(readOneProduct(nombre),getImageFromOneProduct(nombre));
        }
        return result;
    }


    public Producto getSimpleProduct(int id){
        return productoDAO.getProductByID(id);
    }

    public String crearSmartTag(String productName){
        try {
            String smartTag = SmartTag.createSmartTag(productName);
            return QRGenerator.crearQR(smartTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getPrecioProducto(String vendorName, String productName){
        HashMap vendedores = (HashMap) productoDAO.readOne(productName).get(3);
        return (double) vendedores.get(vendorName);
    }

    public double getPrecioProducto(int id_vendedor, int id_producto){
        Producto producto = productoDAO.getProductByID(id_producto);
        String nombre = producto.getNombre();
        String vendedor = vendedorServices.getVendorNameWithID(id_vendedor);
           return getPrecioProducto(vendedor, nombre);
    }

    public int getStockFromOneProductAndOneVendor(String productName, String vendorName){
       return productoDAO.getStockFromOneProduct(productName, vendorName);
    }


    public List<Producto> getProductsByVendor(String vendorName){
        int id_vendedor = usuarioDAO.getID(vendorName);
        List<Producto> result = new ArrayList<>();
        for(Producto p : readAllProducts()){
            Map vendedores = (Map)  productoDAO.readOne(p.getNombre()).get(3);
            if(vendedores.containsKey(vendorName)){
                result.add(p);
            }
        }
        return result;
    }

    public Map getStats(String productName){
        return precioDAO.getStats(productName);
    }

}
