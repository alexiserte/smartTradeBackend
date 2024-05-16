package com.smartTrade.backend;

import com.smartTrade.backend.Fachada.ProductoFachada;
import com.smartTrade.backend.Factory.ConverterFactory;
import com.smartTrade.backend.Utils.Converter;
import com.smartTrade.backend.Utils.PNGConverter;
import com.smartTrade.backend.Utils.ReflectionMethods;
import org.junit.jupiter.api.Test;

import com.smartTrade.backend.Logger.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;

public class Sprint3Test {

    private Logger logger = Logger.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();

    @Test
    void comprobarQueSeSubeUnaImágenCorrectamente() {
        try{
            ConverterFactory converterFactory = new ConverterFactory();
            PNGConverter converter = (PNGConverter) converterFactory.createConversor(".png");
            String base64 = converter.procesar(ProductoFachada.DEFAULT_IMAGE);
            String nombreDelProducto = "Piedra%20de%20Afilar";

            HttpResponse<String> response = conexion.get("/producto/imagen/?name=" + nombreDelProducto);
            String image = response.body();

            assert response.statusCode() == 200;
            assert image != null;
            assert !image.isEmpty();
            assert image.startsWith("data:image/");
            assert image.contains("base64");
            assert !image.equals(base64);
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
