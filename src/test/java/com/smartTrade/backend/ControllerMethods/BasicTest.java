package com.smartTrade.backend.ControllerMethods;

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

    private final smartTradeConexion conexion = new smartTradeConexion();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = Logger.getInstance();

    @Test
    public void serverIsRunning() {
        try {
            HttpResponse<String> response = conexion.get("/health");
            assertEquals(200, response.statusCode());
            assertEquals("Servidor en funcionamiento", response.body());
            logger.logTestResult("serverIsRunning", true);
        } catch (AssertionError error) {
            logger.logTestResult("serverIsRunning", false);
            throw error;
        } catch (Exception e1) {
            logger.logError(e1);
        }
    }

    @Test
    public void welcomeMessage() {
        try {
            HttpResponse<String> response = conexion.get("/");
            assertEquals(200, response.statusCode());
            assertEquals("¡Bienvenido a smartTrade!", response.body());
            logger.logTestResult("welcomeMessage", true);
        } catch (AssertionError error) {
            logger.logTestResult("welcomeMessage", false);
            throw error;
        } catch (Exception e1) {
            logger.logError(e1);
        }
    }

    @Test
    public void teamMembers() {
        try {
            HttpResponse<String> response = conexion.get("/five-guys");
            assertEquals(200, response.statusCode());

            List<MiembroEquipo> miembros = mapper.readValue(response.body(), new TypeReference<List<MiembroEquipo>>() {
            });
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
            logger.logTestResult("teamMembers", true);
        } catch (AssertionError e) {
            logger.logTestResult("teamMembers", false);
            throw new RuntimeException(e);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
