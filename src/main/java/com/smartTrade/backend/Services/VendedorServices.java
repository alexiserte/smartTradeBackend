package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.VendedorDAO;
import com.smartTrade.backend.Models.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VendedorServices {

    @Autowired
    private VendedorDAO vendedorDAO;

    public void createNewVendedor(String nombre, String password, String correo, String direccion){
        vendedorDAO.create(nombre, password, correo, direccion);
    }

    public void updateVendedor(String nickname, Map atributos){
        vendedorDAO.update(nickname, atributos);
    }

    public void deleteVendedor(String nickname){
        vendedorDAO.delete(nickname);
    }

    public Vendedor readOneVendedor(String nickname){
        return vendedorDAO.readOne(nickname);
    }

    public List<Vendedor> readAllVendedores(){
        return vendedorDAO.readAll();
    }

    public String getVendorNameWithID(int id){
        return vendedorDAO.getVendorName(id);
    }

    public int cantidadDeProductosVendidos(String nickname){
        return vendedorDAO.productosVendidosPorUnVendedor(nickname);
    }

    public Vendedor getVendedorWithID(int id_usuario){
        return vendedorDAO.getVendedorWithID(id_usuario);
    }

}
