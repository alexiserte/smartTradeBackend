package com.smartTrade.backend.Facade;

import com.smartTrade.backend.Services.GuardarMasTardeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GuardarMasTardeFachada extends Fachada{

    public ResponseEntity<?> create(String compradorName){
        try {
            guardarMasTardeServices.create(compradorName);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> insertarProducto(Map<String,?> args){
        try {
            String userNickname = (String) args.get("userNickname");
            String productName = (String) args.get("productName");
            String vendorName = (String) args.get("vendorName");
            guardarMasTardeServices.insertarProducto(userNickname, productName, vendorName);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteProducto(Map<String,?> args){
        try{
        String userNickname = (String) args.get("userNickname");
        String productName = (String) args.get("productName");
        String vendorName = (String) args.get("vendorName");
        guardarMasTardeServices.deleteProducto(userNickname,productName,vendorName);
        return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> vaciarLista(String userNickname){
        try {
            guardarMasTardeServices.vaciarLista(userNickname);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> readOne(String userNickname) {
        try {
            return ResponseEntity.ok(guardarMasTardeServices.readOne(userNickname));
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> moverGuardadoMasTardeACarrito(Map<String,?> args){
        try {
            String userNickname = (String) args.get("userNickname");
            String productName = (String) args.get("productName");
            String vendorName = (String) args.get("vendorName");
            guardarMasTardeServices.moverGuardadoMasTardeACarrito(userNickname, productName, vendorName);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
