package com.smartTrade.backend.mappers;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMapper implements ResultSetExtractor<List<String>> {
    @Override
    public List<String> extractData(ResultSet rs) throws SQLException {
        List<String> databases = new ArrayList<>();
        while (rs.next()) {
            String databaseName = rs.getString(1); // Suponiendo que el nombre de la base de datos está en la primera columna
            databases.add(databaseName);
        }
        return databases;
    }
}
