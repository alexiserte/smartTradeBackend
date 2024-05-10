package com.smartTrade.backend.Fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartTrade.backend.DAO.ProductoDAO;
import com.smartTrade.backend.Logger.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.Models.Administrador;
import com.smartTrade.backend.Models.Categoria;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.Vendedor;

@Component
public class AdministradorFachada extends Fachada {

    public ResponseEntity<?> mostrarCategorias() {
        return new ResponseEntity<>(categoriaDAO.readAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarCategoriasPrincipales() {
        return new ResponseEntity<>(categoriaDAO.getCategoriasPrincipales(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarBasesDeDatos() {
        return new ResponseEntity<>(systemDAO.readAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarUsuarios() {
        return new ResponseEntity<>(compradorDAO.readAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarVendedores() {
        return new ResponseEntity<>(vendedorDAO.readAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarProductos(boolean oldMode) {
        if(oldMode) return new ResponseEntity<>(productoDAO.readAll(), HttpStatus.OK);
        else{
            List<Producto> listaDeProductos = productoDAO.readAll();
            List<ProductoDAO.ProductoAntiguo> listaDeProductosAntiguos = new ArrayList<>();
            for(Producto p : listaDeProductos){
                String imagen = productoDAO.getImageFromOneProduct(p.getNombre());
                ProductoDAO.ProductoAntiguo productoAntiguo = new ProductoDAO.ProductoAntiguo(p,imagen);
                listaDeProductosAntiguos.add(productoAntiguo);
            }
            return new ResponseEntity<>(listaDeProductosAntiguos, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> mostrarProductosPendientesDeValidacion() {
        try {
            List<Producto> res = productoDAO.getProductosPendientesDeValidacion();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los productos pendientes de validación",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> existenSubcategorias(String name) {
        try {
            try {
                boolean res = categoriaDAO.hasSubcategories(name);

                @SuppressWarnings("unused")
                class Result {
                    private String name;
                    private boolean hasSubcategories;

                    public Result(String name, boolean hasSubcategories) {
                        this.name = name;
                        this.hasSubcategories = hasSubcategories;
                    }

                    public String getName() {
                        return name;
                    }

                    public boolean getHasSubcategories() {
                        return hasSubcategories;
                    }
                }

                Result r = new Result(name, res);

                return new ResponseEntity<>(r, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("No se encontraron subcategorías", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener las subcategorías", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener las subcategorías", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> mostrarCategorias(String name, Integer id) {
        try {
            if (name == null && id == null) {
                return new ResponseEntity<>(categoriaDAO.readAll(), HttpStatus.OK);
            }
            if (name != null && id != null) {
                return new ResponseEntity<>("No se pueden enviar ambos parámetros", HttpStatus.BAD_REQUEST);
            }
            if (name != null) {
                Categoria res = categoriaDAO.readOne(name);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            if (id != null) {
                String res = categoriaDAO.getNombre(id);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            return new ResponseEntity<>("Error al obtener las categorías", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener las categorías", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> mostrarSubcategorias(String name) {
        try {
            Categoria c = categoriaDAO.readOne(name);
            try {
                List<Categoria> res = categoriaDAO.getSubcategorias(c.getNombre());
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("No se encontraron subcategorías", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al obtener las subcategorías", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener las subcategorías", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> productsBoughtByUser(String identifier) {
        try {
            Comprador c = compradorDAO.readOne(identifier);
            int result = compradorDAO.productosCompradosPorUnUsuario(identifier);

            class Return {
                private String identifier;
                private int productosComprados;

                public Return(String identifier, Integer productosComprados) {
                    this.identifier = identifier;
                    this.productosComprados = productosComprados;
                }

                public String getIdentifier() {
                    return identifier;
                }

                public int getProductosComprados() {
                    return productosComprados;
                }
            }

            Return r = new Return(identifier, result);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el usuario: " + e.getLocalizedMessage() + "\n",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> productsSoldByOneVendor(String identifier) {
        try {
            Vendedor v = vendedorDAO.readOne(identifier);
            int result = vendedorDAO.productosVendidosPorUnVendedor(identifier);

            class Return {
                private String identifier;
                private int productosVendidos;

                public Return(String identifier, Integer productosVendidos) {
                    this.identifier = identifier;
                    this.productosVendidos = productosVendidos;
                }

                public String getIdentifier() {
                    return identifier;
                }

                public int getProductosVendidos() {
                    return productosVendidos;
                }
            }

            Return r = new Return(identifier, result);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el usuario: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> loginAdministrador(String identifier, String password) {
        if (password == null) { // Si no se envía la contraseña, se asume que se quiere obtener la información
                                // del usuario
            try {
                Administrador administrador = adminDAO.readOne(identifier);
                return new ResponseEntity<>(administrador, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
            }
        } else { // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Administrador administrador = adminDAO.readOne(identifier);
                if (administrador.getPassword().equals(password)) {
                    return new ResponseEntity<>(administrador, HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña incorreta", HttpStatus.UNAUTHORIZED);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
            }
        }
    }

    @SuppressWarnings("unused")
    public ResponseEntity<?> registerAdministrador(HashMap<String, ?> body) {
        String nickname = body.get("nickname").toString();
        String password = body.get("password").toString();
        String correo = body.get("correo").toString();
        String direccion = body.get("direccion").toString();
        try {
            Administrador administrador = adminDAO.readOne(direccion);
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            adminDAO.create(nickname, password, correo, direccion);
            return new ResponseEntity<>("Administrador creado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el usuario: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateAdministrador(String nickname,String password, String correo, String dirección) {
        try {
            Administrador administrador = adminDAO.readOne(nickname);
            Map<String, Object> attributes = new HashMap<>();
            if (password == null && dirección == null && correo == null) {
                return new ResponseEntity<>("No se han enviado atributos para actualizar", HttpStatus.BAD_REQUEST);
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
            adminDAO.update(administrador.getNickname(), attributes);
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar un archivo: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> deleteAdministrador(String  nickname) {
        try{  
            Administrador administrador = adminDAO.readOne(nickname);
           adminDAO.delete(administrador.getNickname());
            return new ResponseEntity<>("Usuario eliminado correctamente",HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Error al eliminar el usuario: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getLog(Integer id){
        try{
            if(id != null){
                return new ResponseEntity<>(Logger.getLog(id),HttpStatus.OK);
            }
            return new ResponseEntity<>(Logger.getFullLog(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener los logs: " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
