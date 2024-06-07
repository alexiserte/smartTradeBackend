package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.AdministradorDAO;
import com.smartTrade.backend.Models.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdministradorServices {

    @Autowired
    private AdministradorDAO administradorDAO;

    public void createNewUser(String nickname, String password, String correo, String direccion, String pais, String ciudad) {
        administradorDAO.create(Map.of("nickname", nickname, "password", password, "correo", correo, "direccion", direccion, "pais", pais, "ciudad", ciudad));
    }

    public void updateUser(String nickname, Map atributos) {
        administradorDAO.update(Map.of("nickname", nickname, "atributos", atributos));
    }

    public void deleteUser(String nickname) {
        administradorDAO.delete(Map.of("nickname", nickname));
    }

    public Administrador readUser(String nickname) {
        return administradorDAO.readOne(Map.of("identifier", nickname));
    }

    public List<Administrador> readAllUsers() {
        return administradorDAO.readAll();
    }

    public Administrador getAdministradorWithID(int id) {
        return administradorDAO.getAdministradorWithID(id);
    }

}
