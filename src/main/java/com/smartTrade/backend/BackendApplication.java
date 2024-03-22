package com.smartTrade.backend;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.models.*;
import java.util.List;
import java.util.ArrayList;
import com.smartTrade.backend.daos.*;

@SpringBootApplication
@RestController
public class BackendApplication {

        @Autowired
        CompradorDAO compradorDAO;

        @GetMapping("/")
        public String mensaje(){
            return "Hello World";
        }
        
        

        @GetMapping("/five-guys")
        public List<MiembroEquipo> equipo(){
            MiembroEquipo m1 = new MiembroEquipo("Alejandro", "Iserte");
            MiembroEquipo m2 = new MiembroEquipo("Laura", "Illán");
            MiembroEquipo m3 = new MiembroEquipo("Carlos", "Ibáñez");
            MiembroEquipo m4 = new MiembroEquipo("Sergio", "Martí");
            MiembroEquipo m5 = new MiembroEquipo("Jennifer", "López");
            
            List<MiembroEquipo> res = new ArrayList<>();
            res.add(m1);
            res.add(m2);
            res.add(m3);
            res.add(m4);
            res.add(m5);

            return res;
        }

        @GetMapping("/comprador/")
        public ResponseEntity<?> comprador(@RequestParam(value = "id", required = false) Integer id,
                @RequestParam(value = "nickname", required = false) String nombre) {
            Comprador comprador = null;

            if (id != null && nombre != null) {
                // Lógica para buscar por ID y nombre
                comprador = compradorDAO.getCompradorByIDAndNombre(id, nombre);
            } else if (id != null) {
                // Lógica para buscar por ID
                comprador = compradorDAO.getCompradorByID(id);
            } else if (nombre != null) {
                // Lógica para buscar por nombre
                comprador = compradorDAO.getCompradorByNombre(nombre);
            }

            if (comprador != null) {
                // Si se encontró un comprador, lo devolvemos como ResponseEntity con código de
                // estado 200 (OK)
                return ResponseEntity.ok(comprador);
            } else {
                // Si no se encontró ningún comprador, devolvemos un JSON con un mensaje de
                // error y un código de estado 404 (Not Found)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"No se encontraron resultados para la búsqueda.\"}");
            }
        }

        
        
        
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
