package com.smartTrade.backend.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.Models.Caracteristica;


public class CaracteristicaMapper implements RowMapper<Caracteristica> {

    @Override
    public Caracteristica mapRow(ResultSet rs, int rowNum) throws SQLException {
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId_caracteristica(rs.getInt("id_caracteristica"));
        caracteristica.setValor(rs.getString("valor"));
        caracteristica.setId_categoria(rs.getInt("id_categoria"));
        caracteristica.setId_producto(rs.getInt("id_producto"));
        return caracteristica;

    }
}