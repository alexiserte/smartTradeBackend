package com.smartTrade.backend.ControllerMethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Categoria;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Utils.ReflectionMethods;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void mostrarCompradores() {
        HttpResponse<String> response = conexion.get("/admin/compradores");
        try {
            List<Comprador> usuarios = mapper.readValue(response.body(), new TypeReference<>() {
            });
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            assertNotEquals(0, usuarios.size());

            for(Comprador c : usuarios){

                HttpResponse<String> response2 = conexion.get("/user/?identifier=" + c.getNickname() + "&password=" + c.getPassword());
                assertEquals(200, response2.statusCode());
                HashMap map = mapper.readValue(response2.body(), HashMap.class);

                assertEquals("COMPRADOR",map.get("userType"));
                assertNotEquals("", c.getNickname());
                assertNotEquals("admin", c.getNickname());
                assertNotEquals("", c.getPassword());
                assertNotEquals("", c.getCorreo());
                assertNotEquals(null, c.getFecha_registro());
                assertTrue(c.getpuntosResponsabilidad() >= 0);
                assertNotEquals("", c.getDireccion());
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
    void mostrarVendedores() {
        HttpResponse<String> response = conexion.get("/admin/vendedores");
        try {
            List<Comprador> usuarios = mapper.readValue(response.body(), new TypeReference<>() {
            });
            System.out.println(response.body());
            assertEquals(200, response.statusCode());
            assertNotEquals(0, usuarios.size());

            for(Comprador c : usuarios){

                HttpResponse<String> response2 = conexion.get("/user/?identifier=" + c.getNickname() + "&password=" + c.getPassword());
                assertEquals(200, response2.statusCode());
                HashMap map = mapper.readValue(response2.body(), HashMap.class);

                assertEquals("VENDEDOR",map.get("userType"));
                assertNotEquals("", c.getNickname());
                assertNotEquals("admin", c.getNickname());
                assertNotEquals("", c.getPassword());
                assertNotEquals("", c.getCorreo());
                assertNotEquals(null, c.getFecha_registro());
                assertTrue(c.getpuntosResponsabilidad() >= 0);
                assertNotEquals("", c.getDireccion());
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
    void mostrarProductosAntiguos(){
        HttpResponse<String> response = conexion.get("/admin/productos/?oldMode=false");
        try {
            List<Map<String, Object>> productos = mapper.readValue(response.body(), new TypeReference<>() {});
            assertEquals(200, response.statusCode());
            assertNotEquals(0, productos.size());
            for(Map<String,Object> productMap : productos){
               assertNotNull(productMap.get("nombre"));
                assertNotNull(productMap.get("id_categoria"));
                assertTrue((int) productMap.get("id_categoria") > 0);
                assertNotNull(productMap.get("imagen"));
                assertTrue(productMap.get("imagen").toString().startsWith("data:image/"));
                assertNotNull(productMap.get("descripcion"));
                assertNotNull(productMap.get("fecha_publicacion"));
                assertNotNull(productMap.get("validado"));
                assertNotNull(productMap.get("huella_ecologica"));
                assertTrue((int) productMap.get("huella_ecologica") >= 0 && (int) productMap.get("huella_ecologica") <= 5);
                assertNotNull(productMap.get("stock"));
                assertTrue((int) productMap.get("stock") >= 0);
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
    void mostrarProductosNuevos(){
        HttpResponse<String> response = conexion.get("/admin/productos/?oldMode=true");
        try {
            List<Map<String, Object>> productos = mapper.readValue(response.body(), new TypeReference<>() {});
            assertEquals(200, response.statusCode());
            assertNotEquals(0, productos.size());
            for(Map<String,Object> productMap : productos){
                assertNotNull(productMap.get("nombre"));
                assertNotNull(productMap.get("id_categoria"));
                assertTrue((int) productMap.get("id_categoria") > 0);
                assertNotNull(productMap.get("id_imagen"));
                assertNotNull(productMap.get("descripcion"));
                assertNotNull(productMap.get("fecha_publicacion"));
                assertNotNull(productMap.get("validado"));
                assertNotNull(productMap.get("huella_ecologica"));
                assertTrue((int) productMap.get("huella_ecologica") >= 0 && (int) productMap.get("huella_ecologica") <= 5);
                assertNotNull(productMap.get("stock"));
                assertTrue((int) productMap.get("stock") >= 0);
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
    void mostrarProductosPendientesDeValidacion(){
        HttpResponse<String> response = conexion.get("/admin/pendientes_validacion");
        try {
            List<Producto> productos = mapper.readValue(response.body(), new TypeReference<>() {});
            assertEquals(200, response.statusCode());
            assertNotEquals(0, productos.size());
            for(Producto p : productos){
                assertNotNull(p.getNombre());
                assertNotNull(p.getId_categoria());
                assertTrue(p.getId_categoria() > 0);
                assertNotNull(p.getDescripcion());
                assertNotNull(p.getFecha_publicacion());
                assertNotNull(p.getValidado());
                assertNotNull(p.getHuella_ecologica());
                assertNotNull(p.getId_imagen());
                assertTrue(p.getId_imagen() > 0);
                assertTrue(p.getHuella_ecologica() >= 0 && p.getHuella_ecologica() <= 5);
                assertNotNull(p.getStock());
                assertTrue(p.getStock() >= 0);

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
    void existenSubcategorias(){
        String categoria = "Alimentación";
        String categoria2 = "Procesados";
        HttpResponse<String> response = conexion.get("/admin/categorias");
        try {
            List<Categoria> categorias = mapper.readValue(response.body(), new TypeReference<>() {});
            int alimentacionID = 0;
            int procesadosID = 0;
            for(Categoria c : categorias){
                if(c.getNombre().equals(categoria)) alimentacionID = categorias.indexOf(c);
                else if(c.getNombre().equals(categoria2)) procesadosID = categorias.indexOf(c);
            }

            boolean existenSubcategoriasDeAlimentacion = false;
            boolean existenSubcategoriasDeProcesados = false;

            for(Categoria c : categorias){
                if(c.getCategoria_principal() == alimentacionID) existenSubcategoriasDeAlimentacion = true;
                if(c.getCategoria_principal() == procesadosID) existenSubcategoriasDeProcesados = true;
            }

            assertTrue(existenSubcategoriasDeAlimentacion);
            assertFalse(existenSubcategoriasDeProcesados);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void obtenerUnaCategoria(){
        String category = "Electrónica";
        HttpResponse<String> response = conexion.get("/admin/categoria/?name=" + category);
        System.out.println(response.body());
            Categoria c = null;
            try {
                c = mapper.readValue(response.body(), new TypeReference<>() {});
                assertNotNull(c.getNombre());
                assertNotNull(c.getCategoria_principal());
                assertTrue(c.getCategoria_principal() >= 0);
                assertEquals(c.getNombre(), category);
                assertEquals(0, c.getCategoria_principal());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
        }catch(AssertionError e){
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            throw e;
        }
    }


    @Test
    void listarSubcategorias(){
        String category = "Alimentación";
        HttpResponse<String> response = conexion.get("/admin/categoria/subcategorias/?name=" + category);
        System.out.println(response.body());
        try {
            List<Categoria> subcategorias = mapper.readValue(response.body(), new TypeReference<>() {});
            for(Categoria c : subcategorias){
                assertEquals(1, c.getCategoria_principal());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}


