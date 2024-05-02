package com.smartTrade.backend.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class SystemDAO implements DAOInterface<String>{

    private final JdbcTemplate database;
    public SystemDAO(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public void create(Object... args) {

    }

    @Override
    public String readOne(Object... args) {
        String tableName = (String) args[0];
        StringBuilder result = new StringBuilder();
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        String databaseName = "FiveGuysDatabase";
        List<String> attributes = database.queryForList(sql, String.class, databaseName, tableName);

        result.append("Table: ").append(tableName).append(", Attributes: ");

        for (String attribute : attributes) {
            result.append(attribute).append(" ");
        }

        return result.toString();
    }



    @Override
    public List<String> readAll() { // This method  returns all the tables in the database
            List<String> result = new ArrayList<>();
            String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'FiveGuysDatabase' AND TABLE_TYPE = 'BASE TABLE'";
            List<String> tables = database.queryForList(sql, String.class);
            for (String table : tables) {
                String attributesSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table
                        + "'";
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

    @Override
    public void update(Object... args) { /* no implementado */}

    @Override
    public void delete(Object... args) {
        String tableName = (String) args[0];
        String sql = "DROP TABLE " + tableName;
    }
}
