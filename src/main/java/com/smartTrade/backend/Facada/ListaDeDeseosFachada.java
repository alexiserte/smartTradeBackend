package com.smartTrade.backend.Facada;

import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoListaDeseos;
import com.smartTrade.backend.Services.CompradorServices;
import com.smartTrade.backend.Services.ListaDeseosServices;
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
public class ListaDeDeseosFachada extends Fachada {

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private VendedorServices vendedorServices;

    @Autowired
    private ListaDeseosServices listaDeseosServices;

    public ResponseEntity<?> getListaDeDeseos(String identifier) {
        try {
            Comprador comprador = compradorServices.readOneComprador(identifier);
            try {
                List<ProductoListaDeseos> productos = listaDeseosServices.getListaDeseosFromUser(comprador.getNickname());

                class Producto_Vendedor {
                    private Producto producto;
                    private String vendedor;
                    private double precio;


                    public Producto_Vendedor(Producto producto, String vendedor, double precio) {
                        this.producto = producto;
                        this.vendedor = vendedor;
                        this.precio = precio;

                    }

                    public Producto getProducto() {
                        return producto;
                    }

                    public String getVendedor() {
                        return vendedor;
                    }

                    public double getPrecio() {
                        return precio;
                    }


                }
                List<Producto_Vendedor> productos_vendedores = new ArrayList<>();
                for (ProductoListaDeseos producto : productos) {
                    Producto p = productoServices.getSimpleProduct(producto.getId_producto());
                    String vendedor = vendedorServices.getVendorNameWithID(producto.getId_vendedor());
                    double precio = productoServices.getPrecioProducto(producto.getId_vendedor(), producto.getId_producto());
                    productos_vendedores.add(new Producto_Vendedor(p,vendedor,precio));
                }

                class ListaDeseos {
                    private String nickname;
                    private int numeroProductos;
                    private List<Producto_Vendedor> productos;
                    private double total;

                    public ListaDeseos(String nickname, int numeroProductos, List<Producto_Vendedor> productos, double total) {
                        this.nickname = nickname;
                        this.numeroProductos = numeroProductos;
                        this.productos = productos;
                        this.total = total;
                    }

                    public List<Producto_Vendedor> getProductos() {
                        return productos;
                    }

                    public double getTotal() {
                        return total;
                    }

                    public String getNickname() {
                        return nickname;
                    }

                    public int getNumeroProductos() {
                        return numeroProductos;
                    }


                }

                return new ResponseEntity<>(new ListaDeseos(comprador.getNickname(),listaDeseosServices.productosEnLista(comprador.getNickname()), productos_vendedores, listaDeseosServices.getPrecioTotal(comprador.getNickname())), HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("La lista de deseos esta vacía.", HttpStatus.NOT_FOUND);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

        }
    }

    public ResponseEntity<?> insertarProducto(String productName, String vendorName, String userNickname) {
        try {
            if (!listaDeseosServices.productInListaDeseos(productName, vendorName, userNickname)) {
                listaDeseosServices.insertarProducto(userNickname, productName, vendorName);
                return new ResponseEntity<>("Producto insertado", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Producto ya existente en la lista de deseos", HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteProduct(String productName, String vendorName, String userNickname){
        try {
            if (!listaDeseosServices.productInListaDeseos(productName, vendorName, userNickname)) {
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            } else {
                listaDeseosServices.eliminarProducto(productName, vendorName, userNickname);
                return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
            }
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }


}


