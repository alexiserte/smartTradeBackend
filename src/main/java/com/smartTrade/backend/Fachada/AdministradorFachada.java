package com.smartTrade.backend.Fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartTrade.backend.DAO.ProductoDAO;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AdministradorServices adminServices;

    @Autowired
    private CategoriaServices categoriaServices;

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private VendedorServices vendedorServices;

    @Autowired
    private AdministradorServices administradorServices;

    @Autowired
    private SystemServices systemServices;

    public ResponseEntity<?> mostrarCategorias() {
        return new ResponseEntity<>(categoriaServices.readAllCategories(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarCategoriasPrincipales() {
        return new ResponseEntity<>(categoriaServices.getCategoriasPrincipales(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarBasesDeDatos() {
        return new ResponseEntity<>(systemServices.getAllTables(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarUsuarios() {
        return new ResponseEntity<>(compradorServices.readAllCompradores(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarVendedores() {
        return new ResponseEntity<>(vendedorServices.readAllVendedores(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostrarProductos(boolean oldMode) {
        if(oldMode) return new ResponseEntity<>(productoServices.readAllProducts(), HttpStatus.OK);
        else{
            List<Producto> listaDeProductos = productoServices.readAllProducts();
            List<ProductoDAO.ProductoAntiguo> listaDeProductosAntiguos = new ArrayList<>();
            for(Producto p : listaDeProductos){
                String imagen = productoServices.getImageFromOneProduct(p.getNombre());
                ProductoDAO.ProductoAntiguo productoAntiguo = new ProductoDAO.ProductoAntiguo(p,imagen);
                listaDeProductosAntiguos.add(productoAntiguo);
            }
            return new ResponseEntity<>(listaDeProductosAntiguos, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> mostrarProductosPendientesDeValidacion() {
        try {
            List<Producto> res = productoServices.readProductosPendientesDeValidacion();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los productos pendientes de validación",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> existenSubcategorias(String name) {
        try {
            try {
                boolean res = categoriaServices.tieneSubcategorias(name);

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
                return new ResponseEntity<>(categoriaServices.readAllCategories(), HttpStatus.OK);
            }
            if (name != null && id != null) {
                return new ResponseEntity<>("No se pueden enviar ambos parámetros", HttpStatus.BAD_REQUEST);
            }
            if (name != null) {
                Categoria res = categoriaServices.readOneCategory(name);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            if (id != null) {
                String res = categoriaServices.getNameFromID(id);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            return new ResponseEntity<>("Error al obtener las categorías", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener las categorías", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> mostrarSubcategorias(String name) {
        try {
            Categoria c = categoriaServices.readOneCategory(name);
            try {
                List<Categoria> res = categoriaServices.getSubcategories(c.getNombre());
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
            Comprador c = compradorServices.readOneComprador(identifier);
            int result = compradorServices.cantidadDeProductosComprados(identifier);

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
            Vendedor v = vendedorServices.readOneVendedor(identifier);
            int result = vendedorServices.cantidadDeProductosVendidos(identifier);

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
                Administrador administrador = adminServices.readUser(identifier);
                return new ResponseEntity<>(administrador, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
            }
        } else { // Si se envía la contraseña, se asume que se quiere hacer login
            try {
                Administrador administrador = adminServices.readUser(identifier);
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
        String password = body.get("user_password").toString();
        String correo = body.get("correo").toString();
        String direccion = body.get("direccion").toString();
        try {
            Administrador administrador = adminServices.readUser(nickname);
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            adminServices.createNewUser(nickname, password, correo, direccion);
            return new ResponseEntity<>("Administrador creado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el usuario: " + e.getLocalizedMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateAdministrador(String nickname,String password, String correo, String dirección) {
        try {
            Administrador administrador = adminServices.readUser(nickname);
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
            adminServices.updateUser(administrador.getNickname(), attributes);
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
            Administrador administrador = adminServices.readUser(nickname);
            adminServices.deleteUser(administrador.getNickname());
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
