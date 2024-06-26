package com.smartTrade.backend.Mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("TABLE_NAME");
    }

} 
