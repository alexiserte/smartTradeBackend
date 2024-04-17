package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.User_Types;
import com.smartTrade.backend.factory.UserFactory;

public class CompradorMapper implements RowMapper<Comprador> {
    
    @Override
    public Comprador mapRow(ResultSet rs, int rowNum) throws SQLException {
        String nickname = (rs.getString("nickname"));
        String password = (rs.getString("user_password"));
        String direccion = (rs.getString("direccion"));
        String correo = (rs.getString("correo"));
        int puntos_responsabilidad = (rs.getInt("puntos_responsabilidad"));
        java.sql.Date fecha = (rs.getDate("fecha_registro"));
        Comprador comprador = (Comprador) UserFactory.createUser(User_Types.COMPRADOR, nickname, password, direccion, correo);
        comprador.setFecha_registro(fecha);
        comprador.setpuntosResponsabilidad(puntos_responsabilidad);
        return comprador;
    }
}