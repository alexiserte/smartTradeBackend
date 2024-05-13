package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.SystemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServices {

    @Autowired
    private SystemDAO systemDAO;


    public String getTableAttributes(String tableName) {
        return systemDAO.readOne(tableName);
    }

    public List<String> getAllTables() {
        return systemDAO.readAll();
    }
}
