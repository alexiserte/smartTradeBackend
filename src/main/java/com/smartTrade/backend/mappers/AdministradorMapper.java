package com.smartTrade.backend.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.Factory.UserFactory;
import com.smartTrade.backend.Models.Administrador;
import com.smartTrade.backend.Models.User_Types;

public class AdministradorMapper implements RowMapper<Administrador> {
    
    @Override
    public Administrador mapRow(ResultSet rs, int rowNum) throws SQLException {
        String nickname = (rs.getString("nickname"));
        String password = (rs.getString("user_password"));
        String direccion = (rs.getString("direccion"));
        String correo = (rs.getString("correo"));
        java.sql.Date fecha = (rs.getDate("fecha_registro"));
        Administrador admin = (Administrador) UserFactory.createUser(User_Types.ADMINISTRADOR, nickname, password, direccion, correo);
        admin.setFecha_registro(fecha);
        return admin;
    }
}