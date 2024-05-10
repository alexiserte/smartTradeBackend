package com.smartTrade.backend.APITest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.MiembroEquipo;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BasicTest {

    private smartTradeConexion conexion = new smartTradeConexion();
    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getInstance();

    @Test
    public void serverIsRunning(){
        logger.logDebug("Testeando si el servidor está en funcionamiento");
        HttpResponse<String> response = conexion.get("/health");
        assertEquals(200, response.statusCode());
        assertEquals("Servidor en funcionamiento", response.body());
    }

    @Test
    public void welcomeMessage() {
        logger.logDebug("Testeando si el mensaje de bienvenida es correcto");
        HttpResponse<String> response = conexion.get("/");
        assertEquals(200, response.statusCode());
        assertEquals("¡Bienvenido a smartTrade!", response.body());
    }

    @Test
    public void teamMembers(){
        logger.logDebug("Testeando si los miembros del equipo son correctos");
        HttpResponse<String> response = conexion.get("/five-guys");
        assertEquals(200, response.statusCode());
        try {
            List<MiembroEquipo> miembros = mapper.readValue(response.body(), new TypeReference<List<MiembroEquipo>>() {});
            assertEquals(5, miembros.size());
            assertEquals("Alejandro", miembros.get(0).getNombre());
            assertEquals("Iserte", miembros.get(0).getApellido());
            assertEquals("Laura", miembros.get(1).getNombre());
            assertEquals("Illán", miembros.get(1).getApellido());
            assertEquals("Carlos", miembros.get(2).getNombre());
            assertEquals("Ibáñez", miembros.get(2).getApellido());
            assertEquals("Sergio", miembros.get(3).getNombre());
            assertEquals("Martí", miembros.get(3).getApellido());
            assertEquals("Jennifer", miembros.get(4).getNombre());
            assertEquals("López", miembros.get(4).getApellido());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
