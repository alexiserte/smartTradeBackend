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

    @GetMapping("/admin/categoria")
    public List<Categoria> mostrarCategorias() {
        return categoria.readAll();
    }

    @GetMapping("/admin/database")
    public List<String> mostrarBasesDeDatos() {
        return admin.getAllDatabases();
    }

    @GetMapping("/admin/comprador")
    public List<Comprador> mostrarUsuarios() {
        return comprador.readAll();
    }

    @GetMapping("/admin/vendedor")
    public List<Vendedor> mostrarVendedores() {
        return vendedor.readAll();
    }

    @GetMapping("/admin/producto")
    public List<Producto> mostrarProductos() {
        return producto.readAll();
    }

    
    @GetMapping("/admin/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
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
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
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
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "direction", required = false) String dirección) {
        try {
            Administrador administrador = admin.readOne(nickname);
            Map<String, Object> attributes = new HashMap<>();
            if(password == null && dirección == null){
                return new ResponseEntity<>("No se han enviado atributos para actualizar",HttpStatus.BAD_REQUEST);
            }
            if (password != null) {
                attributes.put("user_password", password);
            }
            if (dirección != null) {
                attributes.put("direccion", dirección);
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
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "nickname", required = true) String  nickname) {
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

