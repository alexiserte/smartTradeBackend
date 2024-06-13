package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.ListaDeDeseosDAO;
import com.smartTrade.backend.Models.ProductoListaDeseos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ListaDeseosServices {

    @Autowired
    private ListaDeDeseosDAO listaDeDeseosDAO;

    public void createNewListaDeseos(String nombre) {
        listaDeDeseosDAO.create(Map.of("compradorName", nombre));
    }

    public List<ProductoListaDeseos> getListaDeseosFromUser(String nombre) {
        return listaDeDeseosDAO.getListaDeseosFromUser(nombre);
    }

    public void insertarProducto(String userNickname, String productName, String vendorName) {
        listaDeDeseosDAO.insertarProducto(userNickname, productName, vendorName);
    }

    public void eliminarProducto(String userNickname, String productName, String vendorName) {
        listaDeDeseosDAO.deleteProducto(userNickname, productName, vendorName);
    }

    public void vaciarLista(String userNickname) {
        listaDeDeseosDAO.vaciarLista(userNickname);
    }

    public boolean productInListaDeseos(String productName, String vendorName, String userNickname) {
        return listaDeDeseosDAO.productInListaDeseos(productName, vendorName, userNickname);
    }

    public double getPrecioTotal(String userName) {
        return listaDeDeseosDAO.getTotalPrice(userName);
    }

    public int productosEnLista(String userNickname) {
        return listaDeDeseosDAO.productosInListaDeseos(userNickname);
    }

}
