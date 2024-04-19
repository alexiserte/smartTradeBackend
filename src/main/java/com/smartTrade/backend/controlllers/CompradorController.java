package com.smartTrade.backend.controlllers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartTrade.backend.daos.*;
import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.models.User_Types;

@RestController
public class CompradorController {

    @Autowired
    CompradorDAO compradorDAO;

    @Autowired
    ProductoDAO productoDAO;

    @GetMapping("/comprador/")
    public ResponseEntity<?> login(@RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "password", required = false) String password) {
        if(password == null){ // Si no se envía la contraseña, se asume que se quiere obtener la información del usuario
            try{
                Comprador comprador = compradorDAO.readOne(identifier);
                return new ResponseEntity<>(comprador,HttpStatus.OK);
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("Usuario no existente",HttpStatus.NOT_FOUND);
            }catch(Exception e){
                return new ResponseEntity<>("Error al obtener usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{ // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Comprador comprador = compradorDAO.readOne(identifier);
                if (comprador.getPassword().equals(password)) {
                    MultiValueMap<String, String> headerHashMap = new LinkedMultiValueMap<>();
                    headerHashMap.add("userType",User_Types.COMPRADOR.toString());
                    return new ResponseEntity<>(comprador,headerHashMap,HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña incorrecta",HttpStatus.UNAUTHORIZED);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no encontrado.",HttpStatus.NOT_FOUND);
            } 
        }
    }

    @SuppressWarnings("unused")
    @PostMapping("/comprador/")
    public ResponseEntity<?> register(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo,
            @RequestParam(value = "direction", required = true) String direccion){
        try{
            Comprador comprador = compradorDAO.readOne(nickname);
            return new ResponseEntity<>("El usuario ya existe",HttpStatus.CONFLICT);
        }catch(EmptyResultDataAccessException e){
            compradorDAO.create(nickname, password,correo,direccion);
            return new ResponseEntity<>("Comprador creado correctamente",HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error al crear el usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    @DeleteMapping("/comprador/")
    public ResponseEntity<?> deleteComprador(@RequestParam(value = "nickname", required = true) String  nickname) {
        try{
            Comprador comprador = compradorDAO.readOne(nickname);    
            compradorDAO.delete(nickname);
            return new ResponseEntity<>("Usuario eliminado correctamente",HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al eliminar el usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/comprador/")
    public ResponseEntity<?> updateComprador(@RequestParam(value = "nickname", required = true) String nickname,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "mail", required = false) String mail,
                                            @RequestParam(value = "direction", required = false) String dirección,
                                            @RequestParam(value = "points", required = false) String puntosResponsabilidad)
    {
        try{
            Comprador comprador = compradorDAO.readOne(nickname);
            if(password == null && dirección == null && puntosResponsabilidad == null && mail == null){
                return new ResponseEntity<>("No se ha enviado ningún atributo para actualizar",HttpStatus.BAD_REQUEST);
            }
            Map<String,Object> attributes = new HashMap<>();
            if(password != null){
                attributes.put("user_password", password);
            }
            if(dirección != null){
                attributes.put("direccion", dirección);
            }
            if(puntosResponsabilidad != null){
                attributes.put("puntos_responsabilidad", Integer.parseInt(puntosResponsabilidad));
            }
            if(mail != null){
                attributes.put("correo", mail);
            }

            compradorDAO.update(comprador.getNickname(), attributes);
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al actualizar un archivo: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

   
    @GetMapping("/comprador/productos_comprados/")
    public ResponseEntity<?> productosComprados(@RequestParam(name = "nickname", required = true) String nickname) {
        try {
            Comprador comprador = compradorDAO.readOne(nickname);
            try{
                List<Producto> productos = productoDAO.getProductosComprados(comprador.getNickname());
                return new ResponseEntity<>(productos, HttpStatus.OK);
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("No se han encontrado productos comprados por este usuario", HttpStatus.NOT_FOUND);
            }catch(Exception e){
                return new ResponseEntity<>("Error al obtener los productos comprados: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los productos comprados: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
