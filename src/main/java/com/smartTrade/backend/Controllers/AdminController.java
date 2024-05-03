package com.smartTrade.backend.Controllers;

import com.smartTrade.backend.Fachada.AdministradorFachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class AdminController {

    @Autowired
    AdministradorFachada fechada;

    @GetMapping("/admin/categorias")
    public ResponseEntity<?> mostrarCategorias() {
        return fechada.mostrarCategorias();
    }
    
    
    @GetMapping("/admin/categorias/principales")
    public ResponseEntity<?> mostrarCategoriasPrincipales() {
        return fechada.mostrarCategoriasPrincipales();
    }
    

    @GetMapping("/admin/database")
    public ResponseEntity<?> mostrarBasesDeDatos() {return fechada.mostrarBasesDeDatos();}

    @GetMapping("/admin/compradores")
    public ResponseEntity<?> mostrarUsuarios() {
        return fechada.mostrarUsuarios();
    }
    

    @GetMapping("/admin/vendedores")
    public ResponseEntity<?> mostrarVendedores() {
        return fechada.mostrarVendedores();
    }


    @GetMapping("/admin/productos")
    public ResponseEntity<?> mostrarProductos(@RequestParam(value = "oldMode", required = false) boolean oldMode){
        if(oldMode) return fechada.mostrarProductos(false);
        else return fechada.mostrarProductos(true);
    }


    @GetMapping("/admin/pendientes_validacion")
    public ResponseEntity<?> mostrarProductosPendientesDeValidacion() {
        return fechada.mostrarProductosPendientesDeValidacion();
    }
    

    @GetMapping("/admin/categoria/existen_subcategorias/")
    public ResponseEntity<?> existenSubcategorias(@RequestParam(value = "name", required = true) String name) {
        return fechada.existenSubcategorias(name);
    }
    

    @GetMapping("/admin/categoria/")
    public ResponseEntity<?> mostrarCategoria(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "id", required = false) Integer id){
        return fechada.mostrarCategorias(name,id);
    }

    @GetMapping("admin/categoria/subcategorias/")
    public ResponseEntity<?> mostrarSubcategorias(@RequestParam(value = "name", required = true) String name){
        return fechada.mostrarSubcategorias(name);
    }
    
    @GetMapping("/admin/productos/comprador/")
    public ResponseEntity<?> productsBoughtByOneBuyer(@RequestParam(value = "identifier", required = true) String identifier){
        return fechada.productsBoughtByUser(identifier);
    }
   
    @GetMapping("/admin/productos/vendedor/")
    public ResponseEntity<?> getProductsFromOneVendor(@RequestParam(value = "identifier", required = true) String identifier){
        return fechada.productsSoldByOneVendor(identifier);
    }
    
    @GetMapping("/admin/")
    public ResponseEntity<?> loginAdministrador(@RequestParam(value = "identifier", required = true) String identifier, @RequestParam(value = "password", required = false) String password){
        return fechada.loginAdministrador(identifier, password);
    }
    

    @PostMapping("/admin/")
    public ResponseEntity<?> registerAdministrador(@RequestBody HashMap<String, ?> body) {
        if (!body.containsKey("nickname") || !body.containsKey("user_password") || !body.containsKey("correo")
                || !body.containsKey("direccion"))
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        return fechada.registerAdministrador(body);
    }
    


    @PutMapping("/admin/")
    public ResponseEntity<?> updateAdministrador(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mail", required = false) String correo,
            @RequestParam(value = "direction", required = false) String dirección) {
        return fechada.updateAdministrador(nickname, password, correo, dirección);
    }


    @DeleteMapping("/admin/")
    public ResponseEntity<?> deleteAdministrador(@RequestParam(value = "nickname", required = true) String nickname) {
        return fechada.deleteAdministrador(nickname);
    }
    
}

