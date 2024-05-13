package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.UsuarioDAO;
import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServices {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private VendedorServices vendedorServices;

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private AdministradorServices administradorServices;

    public User_Types whatTypeIs(String identifier) {
        int id_usuario = usuarioDAO.getID(identifier);

        boolean existsVendor = false;
        boolean existsAdmin = false;
        boolean existsClient = false;

        try {
            Vendedor v = vendedorServices.getVendedorWithID(id_usuario);
            existsVendor = true;
        } catch (EmptyResultDataAccessException ignored) {
        }

        if (!existsVendor) {
            try {
                administradorServices.getAdministradorWithID(id_usuario);
                existsAdmin = true;
            } catch (EmptyResultDataAccessException ignored) {
            }
        }

        if (!existsVendor && !existsAdmin) {
            try {
                compradorServices.getCompradorWithID(id_usuario);
                existsClient = true;
            } catch (EmptyResultDataAccessException ignored) {
            }
        }

        if (existsAdmin) {
            return User_Types.ADMINISTRADOR;
        } else if (existsVendor) {
            return User_Types.VENDEDOR;
        } else if (existsClient) {
            return User_Types.COMPRADOR;
        } else {
            throw new IllegalArgumentException("Los parámetros ingresados no corresponden a un usuario existente en la base de datos.");
        }
    }

}
