package com.smartTrade.backend.Fachada;

import com.smartTrade.backend.Services.AdministradorServices;
import com.smartTrade.backend.Services.CompradorServices;
import com.smartTrade.backend.Services.UsuarioServices;
import com.smartTrade.backend.Services.VendedorServices;
import org.springframework.beans.factory.annotation.Autowired;
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
    

    @Autowired
    CompradorFachada compradorFachada;

    @Autowired
    VendedorFachada vendedorFachada;

    @Autowired
    AdministradorFachada administradorFachada;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private AdministradorServices administradorServices;

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private VendedorServices vendedorServices;

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

        for(String key : body.keySet()){
            System.out.println("---------------------------");
            System.out.println(key + " : " + body.get(key));
            System.out.println("---------------------------");

        }
        switch (userType){
            case "vendedor":

                 return vendedorFachada.registerVendedor(body);
            case "comprador":

            return compradorFachada.register(body);
            case "administrador":

                return administradorFachada.registerAdministrador(body);
            default:
                return new ResponseEntity<>("Tipo de usuario no válido", HttpStatus.BAD_REQUEST);
        }

    }


    @SuppressWarnings("unused") 
    public ResponseEntity<?> login(String identiFier,String password) {
        try {
            User_Types userType = usuarioServices.whatTypeIs(identiFier);

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
                    Administrador administrador = administradorServices.readUser(identiFier);
                    if (administrador.getPassword().equals(password)) {
                        Response<Administrador> response = new Response<>(administrador, User_Types.ADMINISTRADOR);
                        return ResponseEntity.ok(response);
                    } else {
                        return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
                    }
                case VENDEDOR:
                    Vendedor vendedor = vendedorServices.readOneVendedor(identiFier);
                    if (vendedor.getPassword().equals(password)) {
                        Response<Vendedor> response = new Response<>(vendedor, User_Types.VENDEDOR);
                        return ResponseEntity.ok(response);
                    } else {
                        return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
                    }
                case COMPRADOR:
                    Comprador comprador = compradorServices.readOneComprador(identiFier);
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
