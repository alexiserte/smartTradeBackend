package com.smartTrade.backend.controlllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.models.User_Types;
import com.smartTrade.backend.models.Vendedor;

@RestController
public class VendedorController {

    @Autowired
    VendedorDAO vendedorDAO;
    @Autowired
    ProductoDAO productoDAO;

    @GetMapping("/vendedor/")
    public ResponseEntity<?> getVendedor(@RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "password", required = false) String password) {
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
                    MultiValueMap<String, String> headerHashMap = new LinkedMultiValueMap<>();
                    headerHashMap.add("userType",User_Types.VENDEDOR.toString());
                    return new ResponseEntity<>(vendedor,headerHashMap, HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no econtrado", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            } 
        }
    }

    @GetMapping("/vendedor/productos/")
    public ResponseEntity<?> getProductsFromOneVendor(
            @RequestParam(value = "identifier", required = true) String identifier) {
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
    @PostMapping("/vendedor/")
    public ResponseEntity<?> registerVendedor(@RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "mail", required = true) String correo,
            @RequestParam(value = "direccion", required = true) String direccion){
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
    @DeleteMapping("/vendedor/")
    public ResponseEntity<?> deleteVendedor(@RequestParam(value = "nickname", required = true) String  nickname) {
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

    @PutMapping("/vendedor/")
    public ResponseEntity<?> updateVendedor(@RequestParam(value = "nickname", required = true) String nickname,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "direccion", required = false) String dirección,
                                            @RequestParam(value = "mail", required = false) String mail)
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
