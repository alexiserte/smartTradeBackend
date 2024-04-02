package com.smartTrade.backend.controlllers;

import java.util.ArrayList;
import java.util.List;
import com.smartTrade.backend.daos.AdministradorDAO;
import com.smartTrade.backend.daos.CompradorDAO;
import com.smartTrade.backend.models.Comprador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    AdministradorDAO admin;

    @Autowired
    CompradorDAO comprador;

    @GetMapping("/admin/database")
    public List<String> mostrarBasesDeDatos() {
        return admin.getAllDatabases();
    }

    @GetMapping("/admin/compradores")
    public List<Comprador> mostrarUsuarios() {
        return comprador.getAllCompradores();
    }
}

