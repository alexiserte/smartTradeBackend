package com.smartTrade.backend.Services;

import com.smartTrade.backend.Models.Caracteristica;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartTrade.backend.DAO.Caracteristica_ProductoDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaracteristicaServices {

    @Autowired
    Caracteristica_ProductoDAO caracteristicaDAO;

    public void createNewCharacteristic(String nombre, String productName, String vendorName, String valor, String characteristicName) {
        caracteristicaDAO.create(nombre, productName, vendorName, valor, characteristicName);
    }

    public Caracteristica readOneCharacteristic(String nombre, String productName, String vendorName) {
        return caracteristicaDAO.readOne(nombre, productName, vendorName);
    }

    public List<Caracteristica> readAllCharacteristics() {
        return caracteristicaDAO.readAll();
    }

    public void updateCharacteristic(String nombre, String productName, String vendorName, Map atributos) {
        caracteristicaDAO.update(nombre, productName, vendorName, atributos);
    }

    public void deleteCharacteristic(String nombre, String productName, String vendorName) {
        caracteristicaDAO.delete(nombre, productName, vendorName);
    }

    public HashMap<String,String> createSmartTag(String productName){
        return null;
    }


}
