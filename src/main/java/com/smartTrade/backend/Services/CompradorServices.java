package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.CompradorDAO;
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

    public void createNewComprador(String nombre, String password, String correo, String direccion){
        compradorDAO.create(nombre, password, correo, direccion);
    }

    public void updateComprador(String nickname, Map atributos){
        compradorDAO.update(nickname, atributos);
    }

    public Comprador readOneComprador(String nickname){
        return compradorDAO.readOne(nickname);
    }

    public List<Comprador> readAllCompradores(){
        return compradorDAO.readAll();
    }

    public void deleteComprador(String nickname){
        compradorDAO.delete(nickname);
    }

    public int cantidadDeProductosComprados(String nickname){
        return compradorDAO.productosCompradosPorUnUsuario(nickname);
    }

    public Comprador getCompradorWithID(int id_usuario){
        return compradorDAO.getCompradorWithID(id_usuario);
    }

}
