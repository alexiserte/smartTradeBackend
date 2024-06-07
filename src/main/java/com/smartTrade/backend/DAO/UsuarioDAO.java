package com.smartTrade.backend.DAO;

import java.util.List;
import java.util.Map;

import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Usuario;

@Repository
public class UsuarioDAO implements DAOInterface<Usuario> {
    private JdbcTemplate database;

    @Autowired
    CompradorDAO compradorDAO;

    @Autowired
    VendedorDAO vendedorDAO;

    @Autowired
    AdministradorDAO administradorDAO;

    public UsuarioDAO(JdbcTemplate database) {
        this.database = database;
    }


    public void create(Map<String, ?> args) {
    }

    public void update(Map<String, ?> args) {
    }

    public void delete(Map<String, ?> args) {
    }

    public Usuario readOne(Map<String, ?> args) {
        return null;
    }

    public List<Usuario> readAll() {
        return null;
    }

    public int getID(String identifier) {
        return database.queryForObject("SELECT id FROM Usuario WHERE (nickname = ? OR correo = ?);", Integer.class, identifier, identifier);
    }

    public Usuario getUser(int id) {
        try {
            Comprador comprador = compradorDAO.getCompradorWithID(id);
            return comprador;
        } catch (EmptyResultDataAccessException e) {
            try {
                Vendedor vendedor = vendedorDAO.getVendedorWithID(id);
                return vendedor;
            } catch (EmptyResultDataAccessException e2) {
                return administradorDAO.getAdministradorWithID(id);
            }
        }
    }
}
