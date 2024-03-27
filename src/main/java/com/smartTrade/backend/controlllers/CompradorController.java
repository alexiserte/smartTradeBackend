package com.smartTrade.backend.controlllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.daos.*;

@RestController
public class CompradorController {

    @Autowired
    CompradorDAO compradorDAO;

    @GetMapping("/comprador/")
    public ResponseEntity<?> comprador(@RequestParam(value = "id", required = false) Integer id_consumidor,
            @RequestParam(value = "nickname", required = false) String nickname) {
        try {
            if (id_consumidor == null && nickname == null) {
                return ResponseEntity.ok(compradorDAO.getAllCompradores());
            } else if (id_consumidor != null && nickname != null) {
                // Lógica para buscar por ID y nombre
                return ResponseEntity.ok(compradorDAO.getCompradorByIDAndNombre(id_consumidor, nickname));
            } else if (id_consumidor != null) {
                // Lógica para buscar por ID
                return ResponseEntity.ok(compradorDAO.getCompradorByID(id_consumidor));
            } else if (nickname != null) {
                // Lógica para buscar por nombre
                return ResponseEntity.ok(compradorDAO.getCompradorByNombre(nickname));
            } else {
                return ResponseEntity.ok(ResponseEntity.status(400).body("Número de parámetros incorrecto.")); // O //
                                                                                                               // de uso
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(ResponseEntity.status(404).body("Recurso no encontrado."));

        } catch (Exception e) {

            return ResponseEntity.ok(ResponseEntity.status(500).body("Error: " + e.getMessage()));
        }
    }
}
