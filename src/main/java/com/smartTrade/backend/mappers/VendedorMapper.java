package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Vendedor;
import com.smartTrade.backend.factory.UserFactory;
import com.smartTrade.backend.models.User_Types;

public class VendedorMapper implements RowMapper<Vendedor> {
    @Override
    public Vendedor mapRow(ResultSet rs, int rowNum) throws SQLException {
        String nickname = (rs.getString("nickname"));
        String password = (rs.getString("user_password"));
        String direccion = (rs.getString("direccion"));
        String correo = (rs.getString("correo"));
        java.sql.Date fecha = (rs.getDate("fecha_registro"));
        Vendedor vendedor = (Vendedor) UserFactory.createUser(User_Types.VENDEDOR, nickname, password, direccion, correo);
        vendedor.setFecha_registro(fecha);
        return vendedor;
    }
}