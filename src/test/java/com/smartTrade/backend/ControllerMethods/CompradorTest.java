package com.smartTrade.backend.ControllerMethods;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Utils.JSONMethods;
import com.smartTrade.backend.Utils.ReflectionMethods;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompradorTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();
    private final Logger logger = Logger.getInstance();

    @Test
    void getComprador() {
        String nickname = "CompradorDePrueba";
        String password = "password";
        try {
            HttpResponse<String> response = conexion.get("/comprador/?identifier=" + nickname + "&password=" + password);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);


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
        } catch (AssertionError e) {
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
            assertEquals(201, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void deleteComprador() {
        String nickname = "CompradorDePrueba";
        try {
            HttpResponse<String> response = conexion.delete("/comprador/?nickname=" + nickname);

            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
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
            HttpResponse<String> response = conexion.put("/comprador/?nickname=CompradorDePrueba&password=" + password + "&correo=" + correo, JSONMethods.getPrettyJSON(body));

            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }

    }

    @Test
    void cantidadDeProductosComprados() {
        String nicknameCaseOne = "alex1serte";
        String nicknameCaseTwo = "dpuckett4";
        try {
            /*  TEST PARA EL CASO alex1serte    */
            HttpResponse<String> response = conexion.get("/comprador/productos_comprados/?nickname=" + nicknameCaseOne);

            String result = response.body();
            assert response.statusCode() == 404;
            assertEquals("No se han encontrado productos comprados por este usuario", result);

            /*  TEST PARA EL CASO dpuckett4    */
            response = conexion.get("/comprador/productos_comprados/?nickname=" + nicknameCaseTwo);


            assert response.statusCode() == 200;
            //assert lista.size() == 1;


            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getCarritoCompra() {
        String identifier = "alex1serte";
        String discountCode = "PRIMAVERA50";
        try {
            HttpResponse<String> response = conexion.get("/comprador/carrito-compra/?identifier=" + identifier);

            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            List productos = (List) responseBody.get("productos");
            int numProductos = (int) responseBody.get("numeroProductos");


            assert response.statusCode() == 200;
            assert numProductos == 3;
            assert productos.size() == 3;


            HttpResponse<String> response2 = conexion.get("/comprador/carrito-compra/?identifier=" + identifier + "&discountCode=" + discountCode);

            HashMap responseBody2 = mapper.readValue(response2.body(), HashMap.class);
            List productos2 = (List) responseBody2.get("productos");
            int numProductos2 = (int) responseBody2.get("numeroProductos");


            assert response2.statusCode() == 200;
            assert numProductos2 == 3;
            assert productos2.size() == 3;


            double precio1 = (double) responseBody.get("total");
            double precio2 = (double) responseBody2.get("total");

            assert precio1 != precio2;
            assert precio2 == precio1 * 0.5;

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getListaDeseos() {
        String identifier = "TestComprador";
        try {
            HttpResponse<String> response = conexion.get("/comprador/lista-deseos/?identifier=" + identifier);
            assert response.statusCode() == 200;

            HttpResponse<String> response2 = conexion.get("/comprador/lista-deseos/?identifier=SergioMG");
            assert response2.statusCode() == 200;
            HashMap responseBody = mapper.readValue(response2.body(), HashMap.class);
            int productos = (int) responseBody.get("numeroProductos");
            assert productos == 1;

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fullCompradorTest() {
        registerComprador();
        getComprador();
        updateComprador();
        deleteComprador();
        cantidadDeProductosComprados();
    }


}
