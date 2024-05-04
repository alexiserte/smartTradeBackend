package com.smartTrade.backend.Fachada;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.Models.Administrador;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Vendedor;

@Component
public class UsuarioFachada extends Fachada{
    

    @SuppressWarnings("unused") 
    public ResponseEntity<?> register(HashMap<String, ?> body, String userType) {
        switch (userType){
            case "vendedor":
                 return vendedorFachada.registerVendedor(body);
            case "comprador":
            return compradorFachada.register(body);
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
