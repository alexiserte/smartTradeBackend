package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.models.Caracteristica;


public class CaracteristicaMapper implements RowMapper<Caracteristica> {
    
    @Override
    public Caracteristica mapRow(ResultSet rs, int rowNum) throws SQLException {
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setNombre(rs.getString("nombre"));
        caracteristica.setValor(rs.getString("valor"));
        return caracteristica;
        
    }
}