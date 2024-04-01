package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.DatabaseMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AdministradorDAO{
    
    private JdbcTemplate database;

    public AdministradorDAO(JdbcTemplate database) {
        this.database = database;
    }


    public List<String> getAllDatabases() {
        String sql = "USE Five_Guys_DB" + "; " +
                     "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        return database.query(sql, new DatabaseMapper());
    }

}