package com.smartTrade.backend.Fachada;

import com.smartTrade.backend.DAO.ListaDeDeseosDAO;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoListaDeseos;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.smartTrade.backend.Models.Comprador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ListaDeDeseosFachada extends Fachada {
    public ResponseEntity<?> getListaDeDeseos(String identifier) {
        try {
            Comprador comprador = compradorDAO.readOne(identifier);
            try {
                List<ProductoListaDeseos> productos = listaDeDeseosDAO.getListaDeseosFromUser(comprador.getNickname());

                class Producto_Vendedor {
                    private Producto producto;
                    private String vendedor;
                    private double precio;


                    public Producto_Vendedor(Producto producto, String vendedor, double precio, int cantidad) {
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
                    Producto p = productoDAO.getSimpleProducto(producto.getId_producto());
                    String vendedor = vendedorDAO.getVendorName(producto.getId_vendedor());
                    double precio = productoDAO.getPrecioProducto(producto.getId_vendedor(), producto.getId_producto());
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

                return new ResponseEntity<>(new ListaDeseos(comprador.getNickname(), listaDeDeseosDAO.productosInListaDeseos(comprador.getNickname()), productos_vendedores, listaDeDeseosDAO.getTotalPrice(comprador.getNickname())), HttpStatus.OK);
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
            if (!listaDeDeseosDAO.productInListaDeseos(productName, vendorName, userNickname)) {
                listaDeDeseosDAO.insertarProducto(productName, vendorName, userNickname);
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
            if (!listaDeDeseosDAO.productInListaDeseos(productName, vendorName, userNickname)) {
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            } else {
                listaDeDeseosDAO.deleteProduct(productName, vendorName, userNickname);
                return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
            }
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }


}


