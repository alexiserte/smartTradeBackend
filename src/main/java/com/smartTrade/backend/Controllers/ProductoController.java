package com.smartTrade.backend.Controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.jsqlparser.expression.TryCastExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.OffsetScrollPositionHandlerMethodArgumentResolverCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartTrade.backend.Fachada.ProductoFachada;

@RestController
public class ProductoController {

    @Autowired
    ProductoFachada fachada;

    private Logger logger = Logger.getInstance();


    @GetMapping("/productos/")
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "name", required = true) String nombre,
            @RequestParam(name = "category", required = false) String category, HttpServletRequest request) {
        ResponseEntity<?> res = fachada.searchProductByName(nombre, category);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }



    @PostMapping("/producto/")
    public ResponseEntity<?> insertarProducto(@RequestBody(required = true) String body, HttpServletRequest response) throws JsonProcessingException {
        ResponseEntity<?> res = fachada.insertarProducto(body);
        logger.logRequestAndResponse(HttpMethod.POST, response.getRequestURI() + response.getQueryString(), res.toString());
        return res;}



    @DeleteMapping("/producto/{name}/vendedor/{vendor}")
    public ResponseEntity<?> deleteProductFromOneVendor(@PathVariable(name = "name", required = true) String productName,
            @PathVariable(name = "vendor", required = true) String vendorName, HttpServletRequest request) {
        ResponseEntity<?> res = fachada.deleteProductFromOneVendor(productName, vendorName);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @DeleteMapping("/producto/")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "name", required = true) String productName, HttpServletRequest request) {
        ResponseEntity<?> res = fachada.deleteProduct(productName);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @PutMapping("/producto/")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestBody(required = true) HashMap<String, ?> atributos, HttpServletRequest request) {
        ResponseEntity<?> res = fachada.updateProduct(productName, atributos);
        logger.logRequestAndResponse(HttpMethod.PUT, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/producto/")
    public ResponseEntity<?> getProduct(@RequestParam(name = "name", required = true) String productName,
            @RequestParam(name = "image", required = true) boolean image,
                                        @RequestParam(name = "oldMode", required = false) boolean oldMode, HttpServletRequest request) {

        if(!oldMode){
            ResponseEntity<?> res = fachada.getProduct(productName, image);
            logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
            return res;
        }
        else{
            ResponseEntity<?> res = fachada.getOldProduct(productName);
            logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
            return res;
        }
    }

    @GetMapping("/productos/vendedor/")
    public ResponseEntity<?> getProductsFromOneVendor(@RequestParam(name = "identifier", required = true) String vendorName, HttpServletRequest request) {
        ResponseEntity<?> res = fachada.getProductsFromOneVendor(vendorName);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;}



    @PutMapping("/producto/validar/")
    public ResponseEntity<?> validateProduct(@RequestParam(name = "name", required = true) String productName,@RequestParam(name = "vendor", required = true) String vendor, HttpServletRequest request){
        ResponseEntity<?> res = fachada.validarProducto(productName,vendor);
        logger.logRequestAndResponse(HttpMethod.PUT, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
   

  
    @GetMapping("/producto/estadisticas/")
    public ResponseEntity<?> getStatistics(@RequestParam(name = "name", required = true) String productName, HttpServletRequest request){
        ResponseEntity<?> res = fachada.getEstadisticas(productName);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/producto/imagen/")
    public ResponseEntity<?> getImage(@RequestParam(name = "name", required = true) String productName, HttpServletRequest request){
        ResponseEntity<?> res = fachada.getImagen(productName);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/productos/nombres")
    public ResponseEntity<?> getProductsNames(HttpServletRequest request){
        ResponseEntity<?> res = fachada.productAllNames();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/producto/stock/")
    public ResponseEntity<?> getstock(@RequestParam(name = "productName", required = true) String productName,
                                      @RequestParam(name = "vendorName", required = true) String vendorName, HttpServletRequest request){
        ResponseEntity<?> res = fachada.getStockFromOneVendor(productName,vendorName);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

}
