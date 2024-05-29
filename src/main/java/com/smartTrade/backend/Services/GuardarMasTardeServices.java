package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.GuardarMasTardeDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class GuardarMasTardeServices {

    @Autowired
    private GuardarMasTardeDAO guardarMasTardeDAO;

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
}
