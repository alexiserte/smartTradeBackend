package com.smartTrade.backend.ControllerMethods;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Utils.JSONMethods;
import com.smartTrade.backend.Utils.ReflectionMethods;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendedorTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();
    private final Logger logger = Logger.getInstance();

    @Test
    void getVendedor() {
        String nickname = "VendedorDePrueba";
        String password = "password";
        try {
            HttpResponse<String> response = conexion.get("/vendedor/?identifier=" + nickname + "&password=" + password);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);

            assert response.statusCode() == 200;

            assert responseBody.containsKey("nickname");
            assert responseBody.containsKey("correo");
            assert responseBody.containsKey("direccion");
            assert responseBody.containsKey("password");
            assert responseBody.containsKey("fecha_registro");

            assert responseBody.get("password").equals("password");
            assert responseBody.get("nickname").equals(nickname);
            assert responseBody.get("correo").equals("vendedor@pswdds.com");
            assert responseBody.get("direccion").equals("1G - 1E");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerVendedor() {
        HashMap<String, String> body = new HashMap<>();
        body.put("nickname", "VendedorDePrueba");
        body.put("user_password", "password");
        body.put("correo", "vendedor@pswdds.com");
        body.put("direccion", "1G - 1E");
        try {
            HttpResponse<String> response = conexion.post("/vendedor/", JSONMethods.getPrettyJSON(body));

            assertEquals(201, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void deleteVendedor() {
        String nickname = "VendedorDePrueba";
        try {
            HttpResponse<String> response = conexion.delete("/vendedor/?nickname=" + nickname);

            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void updateVendedor() {
        String password = "password";
        String correo = "psw@dds.com";

        HashMap body = new HashMap();
        try {
            HttpResponse<String> response = conexion.put("/vendedor/?nickname=VendedorDePrueba&password=" + password + "&correo=" + correo, JSONMethods.getPrettyJSON(body));

            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void getVendedorProductos() {
        String identifier = "VendedorDePrueba";
        try {
            HttpResponse<String> response = conexion.get("/vendedor/productos/?identifier=" + identifier);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);


            assert response.statusCode() == 200;
            assert responseBody.containsKey("productos");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fullVendedorTest() {
        registerVendedor();
        getVendedor();
        updateVendedor();
        getVendedorProductos();
        deleteVendedor();
    }
}
