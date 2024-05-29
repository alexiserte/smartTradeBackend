package com.smartTrade.backend.Facade;

import com.smartTrade.backend.Services.GuardarMasTardeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class GuardarMasTardeFachada {

    @Autowired
    private GuardarMasTardeServices guardarMasTardeServices;

    public ResponseEntity<?> create(String compradorName){
        guardarMasTardeServices.create(compradorName);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> insertarProducto(Map<String,?> args){
        String userNickname = (String) args.get("userNickname");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        guardarMasTardeServices.insertarProducto(userNickname,productName,vendorName);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteProducto(Map<String,?> args){
        String userNickname = (String) args.get("userNickname");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        guardarMasTardeServices.deleteProducto(userNickname,productName,vendorName);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> vaciarLista(String userNickname){
        guardarMasTardeServices.vaciarLista(userNickname);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> readOne(String userNickname) {
        return ResponseEntity.ok(guardarMasTardeServices.readOne(userNickname));
    }
}
