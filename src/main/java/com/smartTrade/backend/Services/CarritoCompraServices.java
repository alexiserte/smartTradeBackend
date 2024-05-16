package com.smartTrade.backend.Services;

import com.smartTrade.backend.Models.ProductoCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartTrade.backend.DAO.Carrito_CompraDAO;

import java.util.List;

@Service
public class CarritoCompraServices {

    @Autowired
    private Carrito_CompraDAO carritoCompraDAO;

    public void createNewCarritoCompra(String nombre, String producto, String vendedor, int cantidad, double precio) {
        carritoCompraDAO.create(nombre, producto, vendedor, cantidad, precio);
    }

    public List<ProductoCarrito> getCarritoFromUser(String nombre) {
        return carritoCompraDAO.getCarritoFromUser(nombre);
    }

    public int getCantidadFromOneProduct(String productName, String vendorName, String userName) {
        return carritoCompraDAO.getCantidadFromOneProduct(productName, vendorName, userName);
    }

    public void insertarProducto(String productName, String vendorName, String userName) {
        carritoCompraDAO.insertarProduct(productName, vendorName, userName);
    }

    public void eliminarProducto(String productName, String vendorName, String userName) {
        carritoCompraDAO.deleteProduct(productName, vendorName, userName);
    }

    public double getPrecioTotal(String userName) {
        return carritoCompraDAO.getTotalPrice(userName);
    }

    public void aumentarCantidad(String productName, String vendorName, String userName) {
        carritoCompraDAO.aumentarCantidad(productName, vendorName, userName);
    }

    public void disminuirCantidad(String productName, String vendorName, String userName) {
        carritoCompraDAO.disminuirCantidad(productName, vendorName, userName);
    }

    public boolean IsProductInCarrito(String productName, String vendorName, String userName) {
        return carritoCompraDAO.productInCarrito(productName, vendorName, userName);
    }

    public double aplicarDescuento(String userName, String discountCode) {
        return carritoCompraDAO.aplicarDescuento(userName, discountCode);
    }

    public double getDiscount(String code) {
        return carritoCompraDAO.getDiscount(code);
    }

    public void vaciarCarrito(String userName) {
        carritoCompraDAO.vaciarCarrito(userName);
    }

    public int productosEnCarrito(String userName) {
        return carritoCompraDAO.productosInCarrito(userName);
    }

    public void deleteUserCarrito(String userName) {
        carritoCompraDAO.delete(userName);
    }
}
