package com.smartTrade.backend.APITest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Categoria;
import com.smartTrade.backend.Utils.ReflectionMethods;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AdministradorTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();
    private final Logger logger = Logger.getInstance();

    @Test
    void mostrarCategorias() {
        try {
            HttpResponse<String> response = conexion.get("/admin/categorias");
            List<Categoria> categorias = mapper.readValue(response.body(), new TypeReference<List<Categoria>>() {
            });
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            assertNotEquals(0, categorias.size());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        }catch (AssertionError error){
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw error;
        } catch (Exception e1) {
            logger.logError(e1);
        }
    }

    @Test
    void mostrarCategoriasPrincipales() {
        HttpResponse<String> response = conexion.get("/admin/categorias/principales");
        try {
            List<Categoria> categorias = mapper.readValue(response.body(), new TypeReference<List<Categoria>>() {
            });
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            for (Categoria c : categorias) {
                assertEquals(c.getCategoria_principal(), 0);
            }
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError error) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw error;
        } catch (JsonProcessingException e) {
            logger.logError(e);
            throw new RuntimeException(e);
        }
    }

    @Test
    void mostrarBasesDeDatos() {
        HttpResponse<String> response = conexion.get("/admin/database");
        try {
            List<String> databases = mapper.readValue(response.body(), new TypeReference<List<String>>() {
            });
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            assertNotEquals(0, databases.size());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError error) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw error;
        } catch (JsonProcessingException e) {
            logger.logError(e);
            throw new RuntimeException(e);
        }
    }
}
