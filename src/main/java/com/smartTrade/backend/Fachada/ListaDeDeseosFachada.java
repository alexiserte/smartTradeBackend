package com.smartTrade.backend.Fachada;

import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.ProductoCarrito;
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
public class ListaDeDeseosFachada extends Fachada{
    public ResponseEntity<?> getListaDeDeseos(String identifier) {
        try {
            Comprador comprador = compradorDAO.readOne(identifier);
            try{
                List<ProductoListaDeseos> productos = listaDeDeseosDAO.getListaDeseosFromUser(comprador.getNickname());

                class Producto_Vendedor{
                    private Producto producto;
                    private String vendedor;
                    private double precio;


                    public Producto_Vendedor(Producto producto, String vendedor, double precio, int cantidad){
                        this.producto = producto;
                        this.vendedor = vendedor;
                        this.precio = precio;

                    }
                    public Producto getProducto(){
                        return producto;
                    }
                    public String getVendedor(){
                        return vendedor;
                    }
                    public double getPrecio(){
                        return precio;
                    }
                }
            }
        }
    }
}
