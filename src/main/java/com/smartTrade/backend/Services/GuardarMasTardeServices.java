package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.Carrito_CompraDAO;
import com.smartTrade.backend.DAO.GuardarMasTardeDAO;
import com.smartTrade.backend.Models.ProductoCarrito;
import com.smartTrade.backend.Models.ProductoGuardarMasTarde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GuardarMasTardeServices {

    @Autowired
    private GuardarMasTardeDAO guardarMasTardeDAO;

    @Autowired
    private Carrito_CompraDAO carritoCompraDAO;

    public void create(String compradorName){
        guardarMasTardeDAO.create(Map.of("compradorName",compradorName));
    }

    public void insertarProducto(String userNickname, String productName, String vendorName){
        guardarMasTardeDAO.insertarProducto(Map.of("userNickname",userNickname,"productName",productName,"vendorName",vendorName));
    }

    public void deleteProducto(String userNickname, String productName, String vendorName){
        guardarMasTardeDAO.deleteProducto(Map.of("userNickname",userNickname,"productName",productName,"vendorName",vendorName));
    }

    public void vaciarLista(String userNickname){
        guardarMasTardeDAO.vaciarLista(Map.of("userNickname",userNickname));
    }

    public Object readOne(String userNickname) {
       return guardarMasTardeDAO.readOne(Map.of("compradorName",userNickname));
    }

    public void moverGuardadoMasTardeACarrito(String userNickname, String productName, String vendorName){
        carritoCompraDAO.insertarProduct(productName,vendorName,userNickname);
        deleteProducto(userNickname,productName,vendorName);
    }

    public List<ProductoGuardarMasTarde> getGuardarMasTarde(String userNickname){
        return guardarMasTardeDAO.getGuardarMasTarde(userNickname);
    }

    public double getPrecioTotal(String userName) {
        return guardarMasTardeDAO.getTotalPrice(userName);
    }
}
