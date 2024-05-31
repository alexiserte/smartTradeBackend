package com.smartTrade.backend.Facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.util.Pair;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Services.CompradorServices;
import com.smartTrade.backend.Services.PedidoServices;
import com.smartTrade.backend.Services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.Producto;

@Component
public class CompradorFachada extends Fachada {

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private PedidoServices pedidoServices;

    public ResponseEntity<?> login(String identifier, String password) {
        if (password == null) { // Si no se envía la contraseña, se asume que se quiere obtener la información
                                // del usuario
            try {
                Comprador comprador = compradorServices.readOneComprador(identifier);
                return new ResponseEntity<>(comprador, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no existente", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener usuario: " + e.getLocalizedMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else { // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Comprador comprador = compradorServices.readOneComprador(identifier);
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
    public ResponseEntity<?> register(HashMap<String, ?> body) {
        String nickname = (String) body.get("nickname");
        String password = (String) body.get("user_password"); // Se cambió el nombre de la clave de "password" a
                                                              // "user_password"
        String correo = (String) body.get("correo");
        String direccion = (String) body.get("direccion");
        try {
            Comprador comprador = compradorServices.readOneComprador(nickname);
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(":)");
            compradorServices.createNewComprador(nickname, password, correo, direccion);
            return new ResponseEntity<>("Comprador creado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el usuario: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> deleteComprador(String  nickname) {
        try{
            Comprador comprador = compradorServices.readOneComprador(nickname);
            compradorServices.deleteComprador(nickname);
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
            Comprador comprador = compradorServices.readOneComprador(nickname);
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

            compradorServices.updateComprador(comprador.getNickname(), attributes);
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al actualizar un archivo: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

        public ResponseEntity<?> productosComprados(String nickname) {
        try {
            Comprador comprador = compradorServices.readOneComprador(nickname);
            try{
                List<Producto> productos = productoServices.getProductosCompradosPorUsuario(comprador.getNickname());
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

    public ResponseEntity<?> pedidosRealizadosPorUnUsuario(String nickname) {
        try {
            Comprador comprador = compradorServices.readOneComprador(nickname);
            try {
                List<Pedido> pedidos = pedidoServices.readPedidosFromUser(comprador.getNickname());
                return new ResponseEntity<>(pedidos, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("No se han encontrado pedidos realizados por este usuario", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener los pedidos realizados: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los pedidos realizados: " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getProductsFromOnePedidoGivenTheId(int id){
        try{
            List<Producto> productos = pedidoServices.getProductosFromOnePedidoGivenTheID(id);
            return new ResponseEntity<>(productos,HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("No se han encontrado productos en este pedido",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener los productos del pedido: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> createNewPedido (HashMap<String, ?> body){
        try{
            pedidoServices.createNewPedido(body);
            return new ResponseEntity<>("Producto creado correctamente",HttpStatus.CREATED);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("--------",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al crear el pedido: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
