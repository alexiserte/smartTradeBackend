package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.Vendedor;

public class VendedorMapper implements RowMapper<Vendedor> {
    @Override
    public Vendedor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vendedor vendedor = new Vendedor();
        //comprador.setId_comprador(rs.getInt("id"));
        vendedor.setNickname(rs.getString("nickname"));
        vendedor.setPassword(rs.getString("user_password"));
        vendedor.setDireccion(rs.getString("direccion"));
        vendedor.setCorreo(rs.getString("correo"));
        vendedor.setFecha_registro(rs.getDate("fecha_registro"));
        return vendedor;
    }
}