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


    public void createSmartTag(String productName){
        try {
            String smartTag = SmartTag.createSmartTag(productName);
            QRGenerator.crearQR(smartTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SmartTagDAO smartTagDAO = new SmartTagDAO();
        smartTagDAO.createSmartTag("Producto");
    }


}
