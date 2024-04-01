package com.smartTrade.backend.controlllers;

import java.util.ArrayList;
import java.util.List;
import com.smartTrade.backend.daos.AdministradorDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AdminController {

    @Autowired
    AdministradorDAO admin;

    @GetMapping("/admin/database")
    public List<String> mostrarBasesDeDatos() {
        return admin.getAllDatabases();
    }
}

