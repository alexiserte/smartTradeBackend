package com.smartTrade.backend.Fachada;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.Producto;

@Component
public class CompradorFachada extends Fachada {
    public ResponseEntity<?> login(String identifier, String password) {
        if (password == null) { // Si no se envía la contraseña, se asume que se quiere obtener la información
                                // del usuario
            try {
                Comprador comprador = compradorDAO.readOne(identifier);
                return new ResponseEntity<>(comprador, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no existente", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener usuario: " + e.getLocalizedMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else { // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Comprador comprador = compradorDAO.readOne(identifier);
                if (comprador.getPassword().equals(password)) {
                    return new ResponseEntity<>(comprador, HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
            }
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> register(String password, String correo, String direccion,
            HashMap<String, ?> body) {
        String nickname = "";
        try {
            nickname = body.get("nickname").toString();
            Comprador comprador = compradorDAO.readOne(nickname);
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {

            compradorDAO.create(nickname, password, correo, direccion);
            return new ResponseEntity<>("Comprador creado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el usuario: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> deleteComprador(String  nickname) {
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

    public ResponseEntity<?> updateComprador(String nickname,String password,String mail,String dirección,Integer puntosResponsabilidad)
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
                attributes.put("puntos_responsabilidad", puntosResponsabilidad);
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

        public ResponseEntity<?> productosComprados(String nickname) {
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
