package com.smartTrade.backend.Facade;

import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoCarrito;
import com.smartTrade.backend.Models.ProductoGuardarMasTarde;
import com.smartTrade.backend.Services.GuardarMasTardeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GuardarMasTardeFachada extends Fachada {

    public ResponseEntity<?> create(String compradorName) {
        try {
            guardarMasTardeServices.create(compradorName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> insertarProducto(Map<String, ?> args) {
        try {
            String userNickname = (String) args.get("userNickname");
            String productName = (String) args.get("productName");
            String vendorName = (String) args.get("vendorName");
            guardarMasTardeServices.insertarProducto(userNickname, productName, vendorName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteProducto(Map<String, ?> args) {
        try {
            String userNickname = (String) args.get("userNickname");
            String productName = (String) args.get("productName");
            String vendorName = (String) args.get("vendorName");
            guardarMasTardeServices.deleteProducto(userNickname, productName, vendorName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> vaciarLista(String userNickname) {
        try {
            guardarMasTardeServices.vaciarLista(userNickname);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> readOne(String userNickname) {
        try {
            return ResponseEntity.ok(guardarMasTardeServices.readOne(userNickname));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> moverGuardadoMasTardeACarrito(Map<String, ?> args) {
        try {
            String userNickname = (String) args.get("userNickname");
            String productName = (String) args.get("productName");
            String vendorName = (String) args.get("vendorName");
            guardarMasTardeServices.moverGuardadoMasTardeACarrito(userNickname, productName, vendorName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getGuardarMasTarde(String identifier) {
        try {
            Comprador comprador = compradorServices.readOneComprador(identifier);
            try {
                List<ProductoGuardarMasTarde> productos = guardarMasTardeServices.getGuardarMasTarde(comprador.getNickname());

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
                for (ProductoGuardarMasTarde producto : productos) {
                    Producto p = productoServices.getSimpleProduct(producto.getId_producto());
                    String vendedor = vendedorServices.getVendorNameWithID(producto.getId_vendedor());
                    double precio = productoServices.getPrecioProducto(producto.getId_vendedor(), producto.getId_producto());
                    productos_vendedores.add(new Producto_Vendedor(p, vendedor, precio));
                }

                class GuardarMasTarde {
                    private String nickname;
                    private int numeroProductos;
                    private List<Producto_Vendedor> productos;
                    private double total;

                    public GuardarMasTarde(String nickname, int numeroProductos, List<Producto_Vendedor> productos, double total) {
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
                return ResponseEntity.ok(new GuardarMasTarde(comprador.getNickname(), productos.size(), productos_vendedores, guardarMasTardeServices.getPrecioTotal(comprador.getNickname())));
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("El carrito esta vacío.", HttpStatus.NOT_FOUND);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

        }
    }
}
