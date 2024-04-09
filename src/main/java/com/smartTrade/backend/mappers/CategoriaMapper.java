package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Categoria;


public class CategoriaMapper implements RowMapper<Categoria> {
    
    @Override
    public Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setNombre(rs.getString("nombre"));
        categoria.setCategoria_principal(new Categoria(rs.getString("categoria_principal")));
        return categoria;
    }
}