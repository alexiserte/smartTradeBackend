package com.smartTrade.backend.Fachada;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.Models.Administrador;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Vendedor;

import java.util.Arrays;
import java.util.HashMap;

@Component
public class UsuarioFachada extends Fachada{
    

    @SuppressWarnings("unused") 
    public ResponseEntity<?> register(String map) {
        HashMap<String, Object> body = new HashMap<>();
        System.out.println(map);

        String jsonWithoutBackslashes = map.replace("\\", "");
        String pureJSON = jsonWithoutBackslashes.replace("{", "").replace("}", "");
        String[] parts = pureJSON.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            String key = keyValue[0].replace("\"", "").trim();
            String value = keyValue[1].replace("\"", "").trim();
            body.put(key, value);
        }
        String userType = (String) body.get("userType");
        switch (userType){
            case "vendedor":
                VendedorFachada vendedorFachada = new VendedorFachada();
                 return vendedorFachada.registerVendedor(body);
            case "comprador":
                CompradorFachada compradorFachada = new CompradorFachada();
            return compradorFachada.register(body);
            case "administrador":
                AdministradorFachada administradorFachada = new AdministradorFachada();
                return administradorFachada.registerAdministrador(body);
            default:
                return new ResponseEntity<>("Tipo de usuario no válido", HttpStatus.BAD_REQUEST);
        }

    }


    @SuppressWarnings("unused") 
    public ResponseEntity<?> login(String identiFier,String password) {
        try {
            User_Types userType = usuarioDAO.whatTypeIs(identiFier);

            class Response<T> {
                private T user;
                private User_Types userType;

                public Response(T user, User_Types userType) {
                    this.user = user;
                    this.userType = userType;
                }

                public T getUser() {
                    return user;
                }

                public User_Types getUserType() {
                    return userType;
                }
            }

            switch (userType) {
                case ADMINISTRADOR:
                    Administrador administrador = adminDAO.readOne(identiFier);
                    if (administrador.getPassword().equals(password)) {
                        Response<Administrador> response = new Response<>(administrador, User_Types.ADMINISTRADOR);
                        return ResponseEntity.ok(response);
                    } else {
                        return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
                    }
                case VENDEDOR:
                    Vendedor vendedor = vendedorDAO.readOne(identiFier);
                    if (vendedor.getPassword().equals(password)) {
                        Response<Vendedor> response = new Response<>(vendedor, User_Types.VENDEDOR);
                        return ResponseEntity.ok(response);
                    } else {
                        return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
                    }
                case COMPRADOR:
                    Comprador comprador = compradorDAO.readOne(identiFier);
                    if (comprador.getPassword().equals(password)) {
                        Response<Comprador> response = new Response<>(comprador, User_Types.COMPRADOR);
                        return ResponseEntity.ok(response);
                    } else {
                        return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
                    }
                default:
                    return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el usuario: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
