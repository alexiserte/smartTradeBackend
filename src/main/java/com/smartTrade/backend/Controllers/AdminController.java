package com.smartTrade.backend.Controllers;

import com.smartTrade.backend.Facada.AdministradorFachada;
import com.smartTrade.backend.Logger.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class AdminController {

    Logger logger = Logger.getInstance();

    @Autowired
    AdministradorFachada fechada;

    @GetMapping("/admin/categorias")
    public ResponseEntity<?> mostrarCategorias(HttpServletRequest request) {
        ResponseEntity<?> res = fechada.mostrarCategorias();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    
    
    @GetMapping("/admin/categorias/principales")
    public ResponseEntity<?> mostrarCategoriasPrincipales(HttpServletRequest request) {
        ResponseEntity<?> res = fechada.mostrarCategoriasPrincipales();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @GetMapping("/admin/database")
    public ResponseEntity<?> mostrarBasesDeDatos(HttpServletRequest request) {
        ResponseEntity<?> res =  fechada.mostrarBasesDeDatos();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/admin/compradores")
    public ResponseEntity<?> mostrarUsuarios(HttpServletRequest request) {
        ResponseEntity<?> res =  fechada.mostrarUsuarios();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @GetMapping("/admin/vendedores")
    public ResponseEntity<?> mostrarVendedores(HttpServletRequest request) {
        ResponseEntity<?> res =  fechada.mostrarVendedores();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/admin/pedidos")
    public ResponseEntity<?> mostrarPedidos(HttpServletRequest request) {
        ResponseEntity<?> res =  fechada.mostrarTodosLosPedidos();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }


    @GetMapping("/admin/productos/")
    public ResponseEntity<?> mostrarProductos(HttpServletRequest request, @RequestParam(value = "oldMode", required = false) boolean oldMode){
        ResponseEntity<?> res;
        if(oldMode) {res = fechada.mostrarProductos(true);}
        else {res = fechada.mostrarProductos(false);}
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }


    @GetMapping("/admin/pendientes_validacion")
    public ResponseEntity<?> mostrarProductosPendientesDeValidacion(HttpServletRequest request) {
        ResponseEntity<?> res = fechada.mostrarProductosPendientesDeValidacion();
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @GetMapping("/admin/categoria/existen_subcategorias/")
    public ResponseEntity<?> existenSubcategorias(@RequestParam(value = "name", required = true) String name, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.existenSubcategorias(name);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(),res.toString());
        return res;
    }
    

    @GetMapping("/admin/categoria/")
    public ResponseEntity<?> mostrarCategoria(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.mostrarCategorias(name, id);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("admin/categoria/subcategorias/")
    public ResponseEntity<?> mostrarSubcategorias(@RequestParam(value = "name", required = true) String name, HttpServletRequest request){
        ResponseEntity<?> res = fechada.mostrarSubcategorias(name);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    
    @GetMapping("/admin/productos/comprador/")
    public ResponseEntity<?> productsBoughtByOneBuyer(@RequestParam(value = "identifier", required = true) String identifier, HttpServletRequest request){
        ResponseEntity<?> res = fechada.productsBoughtByUser(identifier);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
   
    @GetMapping("/admin/productos/vendedor/")
    public ResponseEntity<?> getProductsFromOneVendor(@RequestParam(value = "identifier", required = true) String identifier, HttpServletRequest request){
        ResponseEntity<?> res = fechada.productsSoldByOneVendor(identifier);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    
    @GetMapping("/admin/")
    public ResponseEntity<?> loginAdministrador(@RequestParam(value = "identifier", required = true) String identifier, @RequestParam(value = "password", required = false) String password, HttpServletRequest request){
        ResponseEntity<?> res = fechada.loginAdministrador(identifier, password);
        logger.logRequestAndResponse(HttpMethod.GET, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    

    @PostMapping("/admin/")
    public ResponseEntity<?> registerAdministrador(@RequestBody HashMap<String, ?> body, HttpServletRequest request) {
        if (!body.containsKey("nickname") || !body.containsKey("user_password") || !body.containsKey("correo")
                || !body.containsKey("direccion")){
            ResponseEntity<?> res =  ResponseEntity.badRequest().body("Faltan campos obligatorios");
            logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
            return res;
        }
        ResponseEntity<?> res = fechada.registerAdministrador(body);
        logger.logRequestAndResponse(HttpMethod.POST, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }
    


    @PutMapping("/admin/")
    public ResponseEntity<?> updateAdministrador(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mail", required = false) String correo,
            @RequestParam(value = "direction", required = false) String dirección, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.updateAdministrador(nickname, password, correo, dirección);
        logger.logRequestAndResponse(HttpMethod.PUT, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }


    @DeleteMapping("/admin/")
    public ResponseEntity<?> deleteAdministrador(@RequestParam(value = "nickname", required = true) String nickname, HttpServletRequest request) {
        ResponseEntity<?> res = fechada.deleteAdministrador(nickname);
        logger.logRequestAndResponse(HttpMethod.DELETE, request.getRequestURI() + request.getQueryString(), res.toString());
        return res;
    }

    @GetMapping("/admin/log/")
    public ResponseEntity<?> getLogs(HttpServletRequest request, @RequestParam(value = "id", required = false) Integer id){
        if(id == null){
            ResponseEntity<?> res = fechada.getLog(null);
            return res;
        }
        ResponseEntity<?> res = fechada.getLog(id);
        return res;
    }
    
}

