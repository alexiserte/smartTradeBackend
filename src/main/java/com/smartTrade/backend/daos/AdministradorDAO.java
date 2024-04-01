package com.smartTrade.backend.daos;

import com.smartTrade.backend.mappers.DatabaseMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdministradorDAO {

    private final JdbcTemplate database;

    public AdministradorDAO(JdbcTemplate database) {
        this.database = database;
    }

    public List<String> getAllDatabases() {
        List<String> result = new ArrayList<>();
        String sql = "USE Five_Guys_DB_Version2; " +
                     "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        List<String> tables = database.queryForList(sql, String.class);
        for (String table : tables) {
            String attributesSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table + "'";
            List<String> attributes = database.queryForList(attributesSql, String.class);
            StringBuilder tableInfo = new StringBuilder();
            tableInfo.append("Table: ").append(table).append(", Attributes: ");
            for (String attribute : attributes) {
                tableInfo.append(attribute).append(" ");
            }
            result.add(tableInfo.toString());
        }
        return result;
    }
}
