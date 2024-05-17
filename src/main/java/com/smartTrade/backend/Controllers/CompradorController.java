package com.smartTrade.backend.Controllers;

import java.util.HashMap;

import com.smartTrade.backend.Facade.CarritoCompraFachada;
import com.smartTrade.backend.Facade.ListaDeDeseosFachada;
import com.smartTrade.backend.Logger.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartTrade.backend.Facade.CompradorFachada;;

@RestController
public class CompradorController {

    @Autowired
    CompradorFachada mediador;

    @Autowired
    CarritoCompraFachada carritoCompraFachada;

    @Autowired
    ListaDeDeseosFachada listaDeDeseosFachada;

    private Logger logger = Logger.getInstance();

    @GetMapping("/comprador/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
                                   @RequestParam(value = "password", required = false) String password, HttpServletRequest request) {

        ResponseEntity<?> res =  mediador.login(identifier, password);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    
    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestBody HashMap<String, ?> body, HttpServletRequest request) {
        if(!body.containsKey("nickname") || !body.containsKey("user_password") || !body.containsKey("correo") || !body.containsKey("direccion")){
            ResponseEntity<?> res = ResponseEntity.badRequest().body("Faltan campos obligatorios");
            logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
            return res;
        }
        ResponseEntity<?> res = mediador.register(body);
        logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }


    @DeleteMapping("/comprador/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "nickname", required = true) String nickname, HttpServletRequest request) {
        ResponseEntity<?> res = mediador.deleteComprador(nickname);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }


    @PutMapping("/comprador/")
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = true) String nickname,
                                           @RequestParam(value = "password", required = false) String password,
                                           @RequestParam(value = "correo", required = false) String correo,
                                           @RequestParam(value = "direccion", required = false) String direccion,
                                           @RequestParam(value = "puntos_responsabilidad", required = false) Integer puntos_responsabilidad, HttpServletRequest request) {
        ResponseEntity<?> res = mediador.updateComprador(nickname, password, correo, direccion, puntos_responsabilidad);
        logger.logRequestAndResponse(HttpMethod.PUT, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

   
    @GetMapping("/comprador/productos_comprados/")
    public ResponseEntity<?> getProductsBought(@RequestParam(value = "nickname", required = true) String nickname, HttpServletRequest request) {
        ResponseEntity<?> res = mediador.productosComprados(nickname);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> getCarritoCompra(@RequestParam(value = "identifier", required = true) String identifier,
                                             @RequestParam(value = "discountCode", required = false) String discountCode,HttpServletRequest request) {
        ResponseEntity<?> res = carritoCompraFachada.getCarritoCompra(identifier, discountCode);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/comprador/lista-deseos/")
    public ResponseEntity<?> getListaDeDeseos(@RequestParam(value = "identifier", required = true) String identifier, HttpServletRequest request) {
        ResponseEntity<?> res = listaDeDeseosFachada.getListaDeDeseos(identifier);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @PutMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> updateCantidad(@RequestParam(value = "productName", required = true) String productName,
                                            @RequestParam(value = "vendorName", required = true) String vendorName,
                                            @RequestParam(value = "userNickname", required = true) String userNickname,
                                            @RequestParam(value = "op", required = true) String op, HttpServletRequest request) {
        ResponseEntity<?> res = carritoCompraFachada.modificarCantidad(userNickname, productName, vendorName, op);
        logger.logRequestAndResponse(HttpMethod.PUT, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }


    @PostMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> addProductCarrito(@RequestParam(value = "productName", required = true) String productName,
                                        @RequestParam(value = "vendorName", required = true) String vendorName,
                                        @RequestParam(value = "userNickname", required = true) String userNickname, HttpServletRequest request) {

        ResponseEntity<?> res = carritoCompraFachada.insertarProducto(productName, vendorName, userNickname);
        logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @PostMapping("/comprador/lista-deseos/")
    public ResponseEntity<?> addProductListaDeseos(@RequestParam(value = "productName", required = true) String productName,
                                        @RequestParam(value = "vendorName", required = true) String vendorName,
                                        @RequestParam(value = "userNickname", required = true) String userNickname, HttpServletRequest request) {

        ResponseEntity<?> res = listaDeDeseosFachada.insertarProducto(productName, vendorName, userNickname);
        logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @DeleteMapping("/comprador/carrito-compra/")
    public ResponseEntity<?> deleteProductCarrito(@RequestParam(value = "productName", required = true) String productName,
                                           @RequestParam(value = "vendorName", required = true) String vendorName,
                                           @RequestParam(value = "userNickname", required = true) String userNickname, HttpServletRequest request) {

        ResponseEntity<?> res = carritoCompraFachada.deleteProduct(productName, vendorName, userNickname);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @DeleteMapping("/comprador/lista-deseos/")
    public ResponseEntity<?> deleteProductListaDeseos(@RequestParam(value = "productName", required = true) String productName,
                                                  @RequestParam(value = "vendorName", required = true) String vendorName,
                                                  @RequestParam(value = "userNickname", required = true) String userNickname, HttpServletRequest request) {

        ResponseEntity<?> res = listaDeDeseosFachada.deleteProduct(productName, vendorName, userNickname);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

}
