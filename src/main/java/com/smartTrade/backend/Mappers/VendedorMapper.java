package com.smartTrade.backend.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.Factory.UserFactory;
import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Vendedor;

public class VendedorMapper implements RowMapper<Vendedor> {
    @Override
    public Vendedor mapRow(ResultSet rs, int rowNum) throws SQLException {
        String nickname = (rs.getString("nickname"));
        String password = (rs.getString("user_password"));
        String direccion = (rs.getString("direccion"));
        String correo = (rs.getString("correo"));
        java.sql.Date fecha = (rs.getDate("fecha_registro"));
        String country = (rs.getString("pais"));
        String city = (rs.getString("ciudad"));
        Vendedor vendedor = (Vendedor) UserFactory.createUser(User_Types.VENDEDOR, nickname, password, direccion, correo, country, city);
        vendedor.setFecha_registro(fecha);
        return vendedor;
    }
}