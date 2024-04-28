package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Utils.SmartTag;
import com.smartTrade.backend.Utils.QRGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SmartTagDAO implements DAOInterface<Object> {
    
    private JdbcTemplate database;

    public SmartTagDAO (JdbcTemplate database) {
        this.database = database;
    }
    @Override
    public void create(Object ...args) {}

    @Override
    public Object readOne(Object ...args) {
        return null;
    }

    @Override
    public void update(Object ...args) {}

    @Override
    public void delete(Object ...args) {}

    @Override
    public List<Object> readAll() {return null;}


    public String createSmartTag(String productName){
        try {
            String smartTag = SmartTag.createSmartTag(productName);
            return QRGenerator.crearQR(smartTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
