package com.smartTrade.backend.Fachada;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.Vendedor;

@Component
public class VendedorFachada extends Fachada{

    public ResponseEntity<?> getVendedor(String identifier, String password) {
        if(password == null){ // Si no se envía la contraseña, se asume que se quiere obtener la información del usuario
            try{
                Vendedor vendedor = vendedorDAO.readOne(identifier);
                return new ResponseEntity<>(vendedor, HttpStatus.OK);
            }catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("Usuario no econtrado", HttpStatus.NOT_FOUND);
            }
            catch(Exception e){
                return new ResponseEntity<>("Error al obtener el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{ // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Vendedor vendedor = vendedorDAO.readOne(identifier);
                if (vendedor.getPassword().equals(password)) {
                    
                    return new ResponseEntity<>(vendedor, HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no econtrado", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            } 
        }
    }


     public ResponseEntity<?> getProductsFromOneVendor(String identifier) {
        try {
            Vendedor vendedor = vendedorDAO.readOne(identifier);
            List<Producto> productos = productoDAO.getProductsBySeller(vendedor.getNickname());
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no econtrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los productos del vendedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @SuppressWarnings("unused")
    public ResponseEntity<?> registerVendedor(HashMap<String, Object> body) {
        String nickname = (String) body.get("nickname");
        String password = (String) body.get("password");
        String correo = (String) body.get("correo");
        String direccion = (String) body.get("direccion");
        try{
            Vendedor vendedor = vendedorDAO.readOne(nickname);
            return new ResponseEntity<>("El usuario ya existe",HttpStatus.CONFLICT);  
        }catch(EmptyResultDataAccessException e){
            vendedorDAO.create(nickname, password,correo,direccion);
            return new ResponseEntity<>("Usuario creado correctamente",HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al crear el usuario",HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> deleteVendedor(String  nickname) {
        try{    
            Vendedor vendedor = vendedorDAO.readOne(nickname);
            vendedorDAO.delete(nickname);
            return new ResponseEntity<>("Usuario eliminado correctamente",HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al eliminar el usuario",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateVendedor(String nickname,String password,String dirección,String mail)
    {
        try{
            Map<String,Object> attributes = new HashMap<>();
            if(password == null && dirección == null && mail == null ){
                return ResponseEntity.ok(ResponseEntity.status(400).body("No se ha enviado ningún atributo para actualizar."));
            }
            if(password != null){
                attributes.put("user_password", password);
            }
            if(dirección != null){
                attributes.put("direccion", dirección);
            }
            if(mail != null){
                attributes.put("correo", mail);
            }

            vendedorDAO.update(nickname, attributes);
            return ResponseEntity.ok(ResponseEntity.status(200).body("Usuario actualizado correctamente."));
        }catch(Exception e){
            return ResponseEntity.ok(ResponseEntity.status(400).body("Error al actualizar el usuario."));
        } 
    }
}