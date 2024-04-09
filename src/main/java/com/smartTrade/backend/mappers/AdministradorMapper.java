package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.models.Administrador;

public class AdministradorMapper implements RowMapper<Administrador> {
    
    @Override
    public Administrador mapRow(ResultSet rs, int rowNum) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setNickname(rs.getString("nickname"));
        administrador.setPassword(rs.getString("user_password"));
        administrador.setDireccion(rs.getString("direccion"));
        administrador.setCorreo(rs.getString("correo"));
        administrador.setFecha_registro(rs.getDate("fecha_registro"));
        return administrador;
    }
}