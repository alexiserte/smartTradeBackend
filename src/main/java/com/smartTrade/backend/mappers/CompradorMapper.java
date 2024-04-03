package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Comprador;

public class CompradorMapper implements RowMapper<Comprador> {
    
    @Override
    public Comprador mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comprador comprador = new Comprador();
        //comprador.setId_comprador(rs.getInt("id"));
        comprador.setNickname(rs.getString("nickname"));
        comprador.setPassword(rs.getString("user_password"));
        comprador.setDireccion(rs.getString("direccion"));
        comprador.setCorreo(rs.getString("correo"));
        comprador.setpuntosResponsabilidad(rs.getInt("puntos_responsabilidad"));
        comprador.setFecha_registro(rs.getDate("fecha_registro"));
        return comprador;
    }
}