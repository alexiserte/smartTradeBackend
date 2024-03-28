package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.smartTrade.backend.models.Vendedor;

public class VendedorMapper implements RowMapper<Vendedor> {
    @Override
    public Vendedor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vendedor Vendedor = new Vendedor();
        Vendedor.setId_vendedor(rs.getInt("id_vendedor"));
        Vendedor.setNombre_vendedor(rs.getString("nombre_vendedor"));
        return Vendedor;
    }
}