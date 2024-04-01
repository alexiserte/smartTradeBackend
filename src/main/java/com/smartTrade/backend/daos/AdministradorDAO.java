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


    public List<String> getAllDatabases(){
        return database.query("SHOW DATABASES",new DatabaseMapper());
    }

}