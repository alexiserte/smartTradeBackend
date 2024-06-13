package com.smartTrade.backend.UseCasesTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Utils.DateMethods;
import com.smartTrade.backend.Utils.JSONMethods;
import com.smartTrade.backend.Utils.ReflectionMethods;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyAProduct {

    private final Logger logger = Logger.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();

    @Test
    void buyAProductTest() {
        try {
            // 1er paso. Registrar un comprador
            Map<String, ?> comprador = Map.of("nickname", "CompradorBuyProductTest", "user_password", "password", "correo", "compradorprueba@psw.com", "direccion", "1G - 1E", "pais", "Italy", "ciudad", "Rome");
            HttpResponse<String> response = conexion.post("/comprador/", JSONMethods.getPrettyJSON(comprador));
            assert response.statusCode() == 201;

            // 2o paso. Añadir un producto al carrito
            Map<String, ?> producto = Map.of("productName", "Piedra de Afilar", "vendorName", "Dynabox", "userNickname", "CompradorBuyProductTest");
            response = conexion.post("/comprador/carrito-compra/?productName=Piedra de Afilar&vendorName=Dynabox&userNickname=CompradorBuyProductTest", null);
            assert response.statusCode() == 200;

            // 3er paso. Comprar el producto haciendo un pedido
            /*****************************************************************************************************/
            // Código auxiliar para obtener el producto.
            String productResponse = conexion.get("/producto/?name=Piedra de Afilar&image=false&oldMode=false").body();
            Map<String, ?> product = mapper.readValue(productResponse, HashMap.class);
            Producto piedraDeAfilar = mapper.readValue(product.get("producto").toString(), Producto.class);
            /*****************************************************************************************************/
            Map<String, Object> pedido = new HashMap<>();
            Pair<Producto, String> productoPair = Pair.of(piedraDeAfilar, "Dynabox");
            pedido.put("productos", Map.of(productoPair, 1));
            pedido.put("nickname", "CompradorBuyProductTest");
            pedido.put("precio_total", 7);
            response = conexion.post("/comprador/pedido/", JSONMethods.getPrettyJSON(pedido));
            assert response.statusCode() == 201;

            // 4o paso. Verificar que el pedido existe y se ha realizado correctamente
            response = conexion.get("/comprador/pedidos/?nickname=CompradorBuyProductTest");
            assert response.statusCode() == 200;
            List<Map> pedidos = mapper.readValue(response.body(), List.class);
            Map<String, ?> ultimoPedidoRealizado = pedidos.get(pedidos.size() - 1);
            assert ultimoPedidoRealizado.get("precio_total").equals(7.0);
            assert ultimoPedidoRealizado.get("fecha_realizacion").equals(DateMethods.getTodayDate());

            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), true);
        } catch (Exception e) {
            logger.logTestResult(ReflectionMethods.obtenerNombreMetodoActual(), false);
            e.printStackTrace();
        }
    }
}
