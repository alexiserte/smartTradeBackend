package com.smartTrade.backend.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Comprador;

public class CompradorMapper implements RowMapper<Comprador> {
    @Override
    public Comprador mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comprador comprador = new Comprador();
        comprador.setId_comprador(rs.getInt("id_consumidor"));
        comprador.setNickname(rs.getString("nickname"));
        comprador.setPassword(rs.getString("user_password"));
        comprador.setDireccion(rs.getString("direccion"));
        comprador.setPuntos_responsabilidad(rs.getInt("puntos_responsabilidad"));
        return comprador;
    }
}