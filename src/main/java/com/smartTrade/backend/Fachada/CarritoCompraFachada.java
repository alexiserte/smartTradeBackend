package com.smartTrade.backend.Fachada;

import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoCarrito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.smartTrade.backend.Models.Comprador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CarritoCompraFachada extends Fachada{
    public ResponseEntity<?> getCarritoCompra(String identifier,String discountCode) {
        try {
            Comprador comprador = compradorDAO.readOne(identifier);
            try{
                List<ProductoCarrito> productos = carritoCompraDAO.getCarritoFromUser(comprador.getNickname());

                class Producto_Vendedor{
                    private Producto producto;
                    private String vendedor;
                    private double precio;
                    private int cantidad;

                    public Producto_Vendedor(Producto producto, String vendedor, double precio, int cantidad){
                        this.producto = producto;
                        this.vendedor = vendedor;
                        this.precio = precio;
                        this.cantidad = cantidad;
                    }
                    public Producto getProducto(){
                        return producto;
                    }
                    public String getVendedor(){
                        return vendedor;
                    }
                    public int getCantidad(){
                        return cantidad;
                    }

                    public double getPrecio(){
                        return precio;
                    }
                }

                List<Producto_Vendedor> productos_vendedores = new ArrayList<>();
                for(ProductoCarrito producto : productos){
                    Producto p = productoDAO.getSimpleProducto(producto.getId_producto());
                    String vendedor = vendedorDAO.getVendorName(producto.getId_vendedor());
                    double precio = productoDAO.getPrecioProducto(producto.getId_vendedor(),producto.getId_producto());
                    productos_vendedores.add(new Producto_Vendedor(p,vendedor,precio,producto.getCantidad()));
                }

                class Carrito{
                    private String nickname;
                    private int numeroProductos;
                    private List<Producto_Vendedor> productos;
                    private String discountCode;
                    private double descuentoAAplicar;
                    private double total;
                    public Carrito(String nickname, int numeroProductos, List<Producto_Vendedor> productos, String discount,double descuento, double total)
                    {
                        this.nickname = nickname;
                        this.numeroProductos = numeroProductos;
                        this.productos = productos;
                        this.discountCode = discount;
                        this.descuentoAAplicar = descuento;
                        this.total = total;
                    }
                    public List<Producto_Vendedor> getProductos(){
                        return productos;
                    }
                    public double getTotal(){
                        return total;
                    }

                    public String getNickname(){
                        return nickname;
                    }

                    public int getNumeroProductos(){
                        return numeroProductos;
                    }
                }

                if(discountCode == null){
                    return new ResponseEntity<>(new Carrito(comprador.getNickname(),carritoCompraDAO.productosInCarrito(comprador.getNickname()),productos_vendedores,null,0,carritoCompraDAO.getTotalPrice(comprador.getNickname())), HttpStatus.OK);
                }else {

                    return new ResponseEntity<>(new Carrito(comprador.getNickname(), carritoCompraDAO.productosInCarrito(comprador.getNickname()), productos_vendedores, discountCode,carritoCompraDAO.getDiscount(discountCode), carritoCompraDAO.aplicarDescuento(identifier, discountCode) ), HttpStatus.OK);
                }
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("El carrito esta vacío.", HttpStatus.NOT_FOUND);
            }
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

        }
    }
}