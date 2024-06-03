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

    public void createNewVendedor(String nombre, String password, String correo, String direccion, String pais, String ciudad){
        vendedorDAO.create(Map.of("nickname",nombre,"password",password,"correo",correo,"direccion",direccion, "pais",pais,"ciudad",ciudad));
    }

    public void updateVendedor(String nickname, Map atributos){
        vendedorDAO.update(Map.of("nickname",nickname,"atributos",atributos));
    }

    public void deleteVendedor(String nickname){
        vendedorDAO.delete(Map.of("nickname",nickname));
    }

    public Vendedor readOneVendedor(String nickname){
        return vendedorDAO.readOne(Map.of("identifier",nickname));
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
