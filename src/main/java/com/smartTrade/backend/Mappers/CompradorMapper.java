package com.smartTrade.backend.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.Factory.UserFactory;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.User_Types;

public class CompradorMapper implements RowMapper<Comprador> {
    
    @Override
    public Comprador mapRow(ResultSet rs, int rowNum) throws SQLException {
        String nickname = (rs.getString("nickname"));
        String password = (rs.getString("user_password"));
        String direccion = (rs.getString("direccion"));
        String correo = (rs.getString("correo"));
        int puntos_responsabilidad = (rs.getInt("puntos_responsabilidad"));
        java.sql.Date fecha = (rs.getDate("fecha_registro"));
        String country = (rs.getString("pais"));
        String city = (rs.getString("ciudad"));
        Comprador comprador = (Comprador) UserFactory.createUser(User_Types.COMPRADOR, nickname, password, direccion, correo, country, city);
        comprador.setFecha_registro(fecha);
        comprador.setpuntosResponsabilidad(puntos_responsabilidad);
        return comprador;
    }
}