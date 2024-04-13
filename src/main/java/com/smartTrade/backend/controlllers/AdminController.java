package com.smartTrade.backend.controlllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartTrade.backend.daos.AdministradorDAO;
import com.smartTrade.backend.daos.CategoriaDAO;
import com.smartTrade.backend.daos.CompradorDAO;
import com.smartTrade.backend.daos.ProductoDAO;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.Categoria;
import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.models.Vendedor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    AdministradorDAO admin;

    @Autowired
    CompradorDAO comprador;

    @Autowired
    VendedorDAO vendedor;

    @Autowired
    ProductoDAO producto;

    @Autowired
    CategoriaDAO categoria;

    @GetMapping("/admin/categorias")
    public List<Categoria> mostrarCategorias() {
        return categoria.readAll();
    }

    @GetMapping("/admin/database")
    public List<String> mostrarBasesDeDatos() {
        return admin.getAllDatabases();
    }

    @GetMapping("/admin/compradores")
    public List<Comprador> mostrarUsuarios() {
        return comprador.readAll();
    }

    @GetMapping("/admin/vendedores")
    public List<Vendedor> mostrarVendedores() {
        return vendedor.readAll();
    }

    @GetMapping("/admin/productos")
    public List<Producto> mostrarProductos() {
        return producto.readAll();
    }

    @GetMapping("/admin/pendientes_validacion")
    public ResponseEntity<?> mostrarProductosPendientesDeValidacion() {
        try{
            List<Producto> res =  producto.getProductosPendientesDeValidacion();
            return new ResponseEntity<>(res,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener los productos pendientes de validación",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/admin/categoria/")
    public ResponseEntity<?> mostrarCategorias(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) Integer id) {
        try{
            if(name == null && id == null){
                return new ResponseEntity<>(categoria.readAll(),HttpStatus.OK);
            }
            if(name != null && id != null){
                return new ResponseEntity<>("No se pueden enviar ambos parámetros",HttpStatus.BAD_REQUEST);
            }
            if(name != null){
                Categoria res = categoria.readOne(name);
                return new ResponseEntity<>(res,HttpStatus.OK);
            }
            if(id != null){
                String res = categoria.getNombre(id);
                return new ResponseEntity<>(res,HttpStatus.OK);
            }
            return new ResponseEntity<>("Error al obtener las categorías",HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener las categorías",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    @GetMapping("/admin/productos/comprador/")
    public ResponseEntity<?> productsBoughtByUser(@RequestParam(value = "identifier", required = true) String identifier){
        try{
            Comprador c = comprador.readOne(identifier);
            int result = comprador.productosCompradosPorUnUsuario(identifier);
            
            class Return{
                private String identifier;
                private int productosComprados;

                public Return(String identifier, Integer productosComprados){
                    this.identifier = identifier;
                    this.productosComprados = productosComprados;
                }

                public String getIdentifier(){
                    return identifier;
                }

                public int getProductosComprados(){
                    return productosComprados;
                }
            }

            Return r = new Return(identifier,result);
            return new ResponseEntity<>(r,HttpStatus.OK);
    }catch(EmptyResultDataAccessException e){
        return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
    
    }catch(Exception e){
        return new ResponseEntity<>("Error al obtener el usuario: " + e.getLocalizedMessage() + "\n",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @SuppressWarnings("unused")
    @GetMapping("/admin/productos/vendedor/")
    public ResponseEntity<?> productsSoldByOneVendor(@RequestParam(value = "identifier", required = true) String identifier){
        try{
            Vendedor v = vendedor.readOne(identifier);
            int result = vendedor.productosVendidosPorUnVendedor(identifier);
            
            class Return{
                private String identifier;
                private int productosVendidos;

                public Return(String identifier, Integer productosVendidos){
                    this.identifier = identifier;
                    this.productosVendidos = productosVendidos;
                }

                public String getIdentifier(){
                    return identifier;
                }

                public int getProductosVendidos(){
                    return productosVendidos;
                }
            }

                Return r = new Return(identifier,result);
                return new ResponseEntity<>(r,HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener el usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/admin/")
    public ResponseEntity<?> loginAdministrador(@RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "password", required = false) String password) {
        if (password == null) { // Si no se envía la contraseña, se asume que se quiere obtener la información
                                // del usuario
            try {
                Administrador administrador = admin.readOne(identifier);
                return new ResponseEntity<>(administrador,HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Usuario no encontrado.",HttpStatus.NOT_FOUND);
            }
        } else { // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Administrador administrador = admin.readOne(identifier);
                if (administrador.getPassword().equals(password)) {
                    return new ResponseEntity<>(administrador,HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña incorreta",HttpStatus.UNAUTHORIZED);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no encontrado.",HttpStatus.NOT_FOUND);
            }
        }
    }

    @SuppressWarnings("unused")
    @PostMapping("/admin/")
    public ResponseEntity<?> registerAdministrador(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo,
            @RequestParam(value = "direccion", required = true) String direccion){
        try{
            Administrador administrador = admin.readOne(direccion);
            return new ResponseEntity<>("El usuario ya existe",HttpStatus.CONFLICT);
        }catch(EmptyResultDataAccessException e){
            admin.create(nickname, password,correo,direccion);
            return new ResponseEntity<>("Administrador creado correctamente",HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error al crear el usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/admin/")
    public ResponseEntity<?> updateAdministrador(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mail", required = false) String correo,
            @RequestParam(value = "direction", required = false) String dirección) {
        try {
            Administrador administrador = admin.readOne(nickname);
            Map<String, Object> attributes = new HashMap<>();
            if(password == null && dirección == null && correo == null){
                return new ResponseEntity<>("No se han enviado atributos para actualizar",HttpStatus.BAD_REQUEST);
            }
            if (password != null) {
                attributes.put("user_password", password);
            }
            if (dirección != null) {
                attributes.put("direccion", dirección);
            }
            if (correo != null) {
                attributes.put("correo", correo);
            }
            admin.update(administrador.getNickname(), attributes);
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar un archivo: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/")
    public ResponseEntity<?> deleteAdministrador(@RequestParam(value = "nickname", required = true) String  nickname) {
        try{  
            Administrador administrador = admin.readOne(nickname);
           admin.delete(administrador.getNickname());
            return new ResponseEntity<>("Usuario eliminado correctamente",HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al eliminar el usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

