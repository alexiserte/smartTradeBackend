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

public class ProductoTest {

    private Logger logger = Logger.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();

    @Test
    void searchProductByName() {
        String productName = "ProductoDePrueba";
        String category = "Electronica";
        try {
            HttpResponse<String> response = conexion.get("/productos/?name=" + productName + "&category=" + category);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("name");
            assert responseBody.containsKey("category");

            assertEquals(productName, responseBody.get("name"));
            assertEquals(category, responseBody.get("category"));

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void insertarProducto() {
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "ProductoDePrueba");
        body.put("category", "Electronica");
        body.put("price", "100.0");
        body.put("stock", "50");
        try {
            HttpResponse<String> response = conexion.post("/producto/", JSONMethods.getPrettyJSON(body));
            System.out.println(JSONMethods.getPrettyJSON(body));
            assertEquals(201, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void deleteProductFromOneVendor() {
        String productName = "ProductoDePrueba";
        String vendorName = "VendedorDePrueba";
        try {
            HttpResponse<String> response = conexion.delete("/producto/" + productName + "/vendedor/" + vendorName);
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void deleteProduct() {
        String productName = "ProductoDePrueba";
        try {
            HttpResponse<String> response = conexion.delete("/producto/?name=" + productName);
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void updateProduct() {
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "ProductoDePrueba");
        body.put("category", "Electronica");
        body.put("price", "150.0");
        body.put("stock", "30");
        try {
            HttpResponse<String> response = conexion.put("/producto/", JSONMethods.getPrettyJSON(body));
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void getProduct() {
        String productName = "ProductoDePrueba";
        boolean image = true;
        boolean oldMode = false;
        try {
            HttpResponse<String> response = conexion.get("/producto/?name=" + productName + "&image=" + image + "&oldMode=" + oldMode);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("name");
            assert responseBody.containsKey("image");

            assertEquals(productName, responseBody.get("name"));

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProductsFromOneVendor() {
        String vendorName = "VendedorDePrueba";
        try {
            HttpResponse<String> response = conexion.get("/productos/vendedor/?identifier=" + vendorName);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("products");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void validateProduct() {
        String productName = "ProductoDePrueba";
        String vendorName = "VendedorDePrueba";
        try {
            HttpResponse<String> response = conexion.put("/producto/validar/?name=" + productName + "&vendor=" + vendorName, "");
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    void getStatistics() {
        String productName = "ProductoDePrueba";
        try {
            HttpResponse<String> response = conexion.get("/producto/estadisticas/?name=" + productName);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("statistics");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getImage() {
        String productName = "ProductoDePrueba";
        try {
            HttpResponse<String> response = conexion.get("/producto/imagen/?name=" + productName);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("image");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProductsNames() {
        try {
            HttpResponse<String> response = conexion.get("/productos/nombres");
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("names");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getStock() {
        String productName = "ProductoDePrueba";
        String vendorName = "VendedorDePrueba";
        try {
            HttpResponse<String> response = conexion.get("/producto/stock/?productName=" + productName + "&vendorName=" + vendorName);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("stock");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getValoracion() {
        String productName = "ProductoDePrueba";
        try {
            HttpResponse<String> response = conexion.get("/producto/valoracion/?productName=" + productName);
            HashMap responseBody = mapper.readValue(response.body(), HashMap.class);
            System.out.println(JSONMethods.getPrettyJSON(responseBody));

            assertEquals(200, response.statusCode());
            assert responseBody.containsKey("valoracion");

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addValoracion() {
        int id_pedido = 123;
        String productName = "ProductoDePrueba";
        int valoracion = 5;
        try {
            HttpResponse<String> response = conexion.put("/producto/valoracion/?id_pedido=" + id_pedido + "&productName=" + productName + "&valoracion=" + valoracion, "");
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (AssertionError e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }

    @Test
    public void fullProductoTest() {
        insertarProducto();
        searchProductByName();
        getProduct();
        getProductsFromOneVendor();
        validateProduct();
        getStatistics();
        getImage();
        getProductsNames();
        getStock();
        getValoracion();
        addValoracion();
        updateProduct();
        deleteProductFromOneVendor();
        deleteProduct();
    }
}
