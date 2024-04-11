package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Categoria_Caracteristica;


public class Categoria_CaracteristicaMapper implements RowMapper<Categoria_Caracteristica> {
    
    @Override
    public Categoria_Caracteristica mapRow(ResultSet rs, int rowNum) throws SQLException {
        Categoria_Caracteristica categoria = new Categoria_Caracteristica();
        categoria.setNombre(rs.getString("nombre"));
        categoria.setId_categoria(rs.getInt("id_categoria"));
        return categoria;
    }
}