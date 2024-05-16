package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.SystemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SystemServices {

    @Autowired
    private SystemDAO systemDAO;


    public String getTableAttributes(String tableName) {
        return systemDAO.readOne(Map.of("tableName",tableName));
    }

    public List<String> getAllTables() {
        return systemDAO.readAll();
    }
}
