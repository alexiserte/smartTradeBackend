package com.smartTrade.backend.Fachada;

import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoCarrito;
import com.smartTrade.backend.Services.CarritoCompraServices;
import com.smartTrade.backend.Services.CompradorServices;
import com.smartTrade.backend.Services.ProductoServices;
import com.smartTrade.backend.Services.VendedorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.smartTrade.backend.Models.Comprador;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarritoCompraFachada extends Fachada{

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private VendedorServices vendedorServices;

    @Autowired
    private CarritoCompraServices carritoCompraServices;


    public ResponseEntity<?> getCarritoCompra(String identifier,String discountCode) {
        try {
            Comprador comprador = compradorServices.readOneComprador(identifier);
            try{
                List<ProductoCarrito> productos = carritoCompraServices.getCarritoFromUser(comprador.getNickname());

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
                    Producto p = productoServices.getSimpleProduct(producto.getId_producto());
                    String vendedor = vendedorServices.getVendorNameWithID(producto.getId_vendedor());
                    double precio = productoServices.getPrecioProducto(producto.getId_vendedor(),producto.getId_producto());
                    productos_vendedores.add(new Producto_Vendedor(p,vendedor,precio,producto.getCantidad()));
                }

                class Carrito{
                    private String nickname;
                    private int numeroProductos;
                    private List<Producto_Vendedor> productos;
                    private String discountCode;
                    private String descuentoAAplicar;
                    private double total;
                    public Carrito(String nickname, int numeroProductos, List<Producto_Vendedor> productos, String discount,double descuento, double total)
                    {
                        this.nickname = nickname;
                        this.numeroProductos = numeroProductos;
                        this.productos = productos;
                        this.discountCode = discount;
                        if(descuento == 1){
                            this.descuentoAAplicar = "¡GRATIS!";
                        }else{
                            double descuentoPorcentaje = descuento * 100;
                            this.descuentoAAplicar = descuentoPorcentaje + "%";
                        }

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

                    public String getDiscountCode(){
                        return discountCode;
                    }

                    public String getDescuentoAAplicar(){
                        return descuentoAAplicar;
                    }
                }

                if(discountCode == null){
                    return new ResponseEntity<>(new Carrito(comprador.getNickname(),carritoCompraServices.productosEnCarrito(comprador.getNickname()),productos_vendedores,null,0,carritoCompraServices.getPrecioTotal(comprador.getNickname())), HttpStatus.OK);
                }else {

                    return new ResponseEntity<>(new Carrito(comprador.getNickname(), carritoCompraServices.productosEnCarrito(comprador.getNickname()), productos_vendedores, discountCode,carritoCompraServices.getDiscount(discountCode), carritoCompraServices.aplicarDescuento(identifier, discountCode) ), HttpStatus.OK);
                }
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("El carrito esta vacío.", HttpStatus.NOT_FOUND);
            }
            catch(IllegalArgumentException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

        }
    }


    public ResponseEntity<String> modificarCantidad(String nickname, String productName, String vendorName, String action) {
        try {
            if (!carritoCompraServices.IsProductInCarrito(productName, vendorName, nickname)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            } else {
                switch (action.toLowerCase()) {
                    case "aumentar":
                        carritoCompraServices.aumentarCantidad(productName, vendorName, nickname);
                        return ResponseEntity.ok("Cantidad aumentada");
                    case "disminuir":
                        carritoCompraServices.disminuirCantidad(productName, vendorName, nickname);
                        return ResponseEntity.ok("Cantidad disminuida");
                    default:
                        return ResponseEntity.badRequest().body("Acción no válida: " + action);
                }
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }




    public ResponseEntity<?> insertarProducto(String productName, String vendorName, String userNickname){
        try {
            if(carritoCompraServices.IsProductInCarrito(productName, vendorName, userNickname)) {
                return modificarCantidad(userNickname, productName, vendorName, "+");
            }
            else{
                carritoCompraServices.insertarProducto(productName, vendorName, userNickname);
                return new ResponseEntity<>("Producto insertado", HttpStatus.OK);
            }
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> deleteProduct(String productName, String vendorName, String userNickname){
        try {
            if (!carritoCompraServices.IsProductInCarrito(productName, vendorName, userNickname)) {
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            } else {
                carritoCompraServices.eliminarProducto(productName, vendorName, userNickname);
                return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
            }
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
