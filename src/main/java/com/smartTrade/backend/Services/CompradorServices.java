package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.CompradorDAO;
import com.smartTrade.backend.DAO.ProductoDAO;
import com.smartTrade.backend.Models.Comprador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompradorServices {

    @Autowired
    private CompradorDAO compradorDAO;

    public void createNewComprador(String nombre, String password, String correo, String direccion, String pais, String ciudad){
        compradorDAO.create(Map.of("nickname",nombre,"password",password,"correo",correo,"direccion",direccion, "pais",pais,"ciudad",ciudad));
    }

    public void updateComprador(String nickname, Map atributos){
        compradorDAO.update(Map.of("nickname",nickname,"atributos",atributos));
    }

    public Comprador readOneComprador(String nickname){
        return compradorDAO.readOne(Map.of("identifier",nickname));
    }

    public List<Comprador> readAllCompradores(){
        return compradorDAO.readAll();
    }

    public void deleteComprador(String nickname){
        compradorDAO.delete(Map.of("nickname",nickname));
    }

    public int cantidadDeProductosComprados(String nickname){
        return compradorDAO.productosCompradosPorUnUsuario(nickname);
    }

    public Comprador getCompradorWithID(int id_usuario){
        return compradorDAO.getCompradorWithID(id_usuario);
    }



}
