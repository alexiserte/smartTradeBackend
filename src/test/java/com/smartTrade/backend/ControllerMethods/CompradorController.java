package com.smartTrade.backend.ControllerMethods;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Utils.JSONMethods;
import com.smartTrade.backend.Utils.ReflectionMethods;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompradorController {

    private Logger logger = Logger.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();

    @Test
    void getComprador() {
        String nickname = "CompradorDePrueba";
        String password = "password";
        try {
            HttpResponse<String> response = conexion.get("/comprador/?identifier=" + nickname + "&password=" + password);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assert response.statusCode() == 200;

            assert responseBody.containsKey("nickname");
            assert responseBody.containsKey("correo");
            assert responseBody.containsKey("direccion");
            assert responseBody.containsKey("password");
            assert responseBody.containsKey("fecha_registro");
            assert responseBody.containsKey("puntosResponsabilidad");

            assert (int) responseBody.get("puntosResponsabilidad") == 0;

            assert responseBody.get("password").equals("password");
            assert responseBody.get("nickname").equals(nickname);
            assert responseBody.get("correo").equals("comprador@pswdds.com");
            assert responseBody.get("direccion").equals("1G - 1E");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        }catch(AssertionError e){
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerComprador() {
        HashMap<String, String> body = new HashMap<>();
        body.put("nickname", "CompradorDePrueba");
        body.put("user_password", "password");
        body.put("correo", "comprador@pswdds.com");
        body.put("direccion", "1G - 1E");
        try {
            HttpResponse<String> response = conexion.post("/comprador/", JSONMethods.getPrettyJSON(body));
            System.out.println(JSONMethods.getPrettyJSON(body));
            assertEquals(201, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        }catch (AssertionError e){
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void deleteComprador() {
        String nickname = "CompradorDePrueba";
        try {
            HttpResponse<String> response = conexion.delete("/comprador/?nickname=" + nickname);
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        }catch (AssertionError e){
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void updateComprador() {
        String password = "password";
        String correo = "psw@dds.com";

        HashMap body = new HashMap();
        try {
            HttpResponse<String> response = conexion.put("/comprador/?nickname=CompradorDePrueba&password=" + password + "&correo=" + correo,JSONMethods.getPrettyJSON(body));
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        }catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }

    }

    @Test
    void fullCompradorTest() {
        registerComprador();
        getComprador();
        updateComprador();
        deleteComprador();
    }


}
