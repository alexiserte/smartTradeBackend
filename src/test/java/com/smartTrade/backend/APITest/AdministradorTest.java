package com.smartTrade.backend.APITest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Models.Categoria;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministradorTest {

    private ObjectMapper mapper = new ObjectMapper();
    private smartTradeConexion conexion = new smartTradeConexion();

    @Test
    void mostrarCategorias() {
        HttpResponse<String> response = conexion.get("/admin/categorias");
        try {
            List<Categoria> categorias = mapper.readValue(response.body(), new TypeReference<List<Categoria>>(){});
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
