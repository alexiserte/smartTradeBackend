package com.smartTrade.backend.daos;
import com.smartTrade.backend.mappers.ProductMapper;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.daos.Caracteristica_ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import com.smartTrade.backend.utils.ImageResizer;
import com.smartTrade.backend.utils.PNGConverter;


@Repository
public class ProductoDAO{
    
    private JdbcTemplate database;

    @Autowired
    VendedorDAO VendedorDAO;

    @Autowired
    Caracteristica_ProductoDAO CaracteristicaDAO;

    public ProductoDAO(JdbcTemplate database) {
        this.database = database;
    }
  
    public void create(String nombre, String characteristicName, String vendorName, double precio, String descripcion,String imagen) {
        String imagenResized = PNGConverter.convertAndResizeImageToBase64(imagen);
        Date fechaActual = new Date(System.currentTimeMillis());
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
        java.util.Random random = new java.util.Random();
        int huella_ecologica = random.nextInt(0,6);
        try{
            database.queryForObject("SELECT nombre, id_categoria, descripcion,imagen,fecha_añadido,validado, huella_ecologica FROM Producto WHERE nombre = ?", new ProductMapper(), nombre);
            int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
            int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?",Integer.class,nombre);
            double precioObject = database.queryForObject("SELECT precio FROM Historico_Precios WHERE id_producto = ?",Double.class,id_producto);
            database.update("INSERT INTO Vendedores_Producto(id_vendedor,id_producto,precio) VALUES(?,?,?)",id_vendedor,id_producto,precioObject);
            database.update("INSERT INTO Historico_Precios(id_producto,precio,fecha_modificacion,id_vendedor) VALUES(?,?,?,?)", id_producto, precio,fechaSQL,id_vendedor);
        }catch(EmptyResultDataAccessException e){
            int id_categoria = database.queryForObject("SELECT id FROM Categoria WHERE nombre = ?", Integer.class, characteristicName);
            int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
            database.update("INSERT INTO Producto(nombre, id_categoria, descripcion,imagen,fecha_añadido,validado,huella_ecologica) VALUES (?, ?,?,?,?,?,?);", nombre, id_categoria, descripcion,imagenResized,fechaSQL, false, huella_ecologica);
            database.update("INSERT INTO Pendientes_Validacion(id_producto) SELECT id FROM Producto WHERE nombre = ?;", nombre);
            int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?",Integer.class,nombre);
            database.update("INSERT INTO Historico_Precios(id_producto,precio,fecha_modificacion,id_vendedor) VALUES(?,?,?)", id_producto, precio,fechaSQL,id_vendedor);
            database.update("INSERT INTO Vendedores_Producto(id_vendedor,id_producto,precio) VALUES(?,?;?)",id_vendedor,id_producto,precio);
        }
    }

    public List<Object> readOne(String productName) {
        
        List<Object> res = new ArrayList<>();
        int id_producto = database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);

        Producto producto = database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion, imagen,fecha_añadido,validado,huella_ecologica FROM Producto WHERE id = ?", new ProductMapper(), id_producto);
        
       
        HashMap<String,String> caracteristicas = CaracteristicaDAO.getSmartTag(productName);
        res.add(0,producto);

        res.add(1,caracteristicas);
        String categoria = database.queryForObject("SELECT nombre FROM Categoria WHERE id IN(SELECT id_categoria FROM Producto WHERE nombre = ?",String.class,productName);
        res.add(2,categoria);

        List<String> vendedores = database.queryForList("SELECT nombre FROM Vendedores_Producto WHERE id_producto = ? ORDER BY id", String.class, id_producto);
        List<Double> precios = database.queryForList("SELECT precio FROM Vendedores_Producto WHERE id_producto = ? ORDER BY id", Double.class, id_producto);

        Map<String,Double> mapaDeVendedores = new HashMap<>();
        for(int i = 0; i < vendedores.size(); i++){
            mapaDeVendedores.put(vendedores.get(i),precios.get(i));
        }

        res.add(3,mapaDeVendedores);

        return res;
    }

    public Producto readOneProduct(String productName) {
       return database.queryForObject("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado, huella_ecologica FROM Producto WHERE nombre = ?", new ProductMapper(), productName);
    }

    public List<Producto> readAll() {
        return database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado,huella_ecologica FROM Producto", new ProductMapper());
    }

/**
 * El método "actualizar" en Java actualiza los atributos del producto en una base de datos en función
 * de los atributos de entrada y la información del proveedor.
 * 
 * @param nombre El parámetro `nombre` en el método `update` representa el nombre del producto que
 * deseas actualizar en la base de datos. Se utiliza para identificar el producto específico que
 * necesita ser modificado.
 * @param vendorName El parámetro `vendorName` en el método `update` representa el nombre del proveedor
 * asociado con el producto que se actualiza. Se utiliza para identificar al proveedor en la base de
 * datos y recuperar la información del producto correspondiente para actualizarla.
 * @param atributos El parámetro `atributos` en el método `update` es un `HashMap` que contiene pares
 * clave-valor que representan los atributos y sus nuevos valores que deben actualizarse para un
 * producto en la base de datos. Las claves en `HashMap` corresponden a los atributos del producto (por
 * ejemplo, "
 */
public void update(String nombre, HashMap<String, ?> atributos) {
    List<String> keys = new ArrayList<>(atributos.keySet());
    Producto product = database.queryForObject(
            "SELECT nombre, id_categoria, descripcion,imagen,fecha_añadido,validado ,huella_ecologica FROM Producto WHERE nombre = ?",
            new ProductMapper(), nombre);

    for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
        String key = iterator.next();
        if (key.equals("nombre")) {
            if (atributos.get(key).equals(product.getNombre())) {
                iterator.remove();
            }
        } else if (key.equals("id_categoria")) {
            if ((int) atributos.get(key) == (product.getId_categoria())) {
                iterator.remove();
            }
        
        } else if (key.equals("descripcion")) {
            if (((String) atributos.get(key)).equals(product.getDescripcion())) {
                iterator.remove();
            }
        } else if (key.equals("imagen")) {
            if (((String) atributos.get(key)).equals(product.getImagen())) {
                iterator.remove();
            }
        } else if (key.equals("fecha_añadido")) {
            if (((Date) atributos.get(key)).equals(product.getFecha_publicacion())) {
                iterator.remove();
            }
        } else if (key.equals("validado")) {
            if ((boolean) atributos.get(key) == product.getValidado()) {
                iterator.remove();
            }
        } else if (key.equals("huella_ecologica")) {
            if ((int) atributos.get(key) == product.getHuella_ecologica()) {
                iterator.remove();
            }
        }

    }

    if (keys.isEmpty()) {
        return;
    }

    for (String key : keys) {
        Object valor = atributos.get(key);
        if (valor instanceof Integer) {
            database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ?",
                    (Integer) valor, nombre);
        } else if (valor instanceof String) {
            database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ?",
                    (String) valor, nombre );
        } else if (valor instanceof Double) {
            database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ?",
                    (Double) valor, nombre );
        }
    }

}



    public void delete(String nombre,String vendorName) {
        int id_vendedor = database.queryForObject("SELECT id_usuario FROM Vendedor WHERE id_usuario IN(SELECT id FROM Usuario WHERE nickname = ?)", Integer.class, vendorName);
        database.update("DELETE FROM Producto WHERE nombre = ?", nombre);
        database.update("DELETE FROM Historico_Precios WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ? AND id_vendedor = ?);", nombre, id_vendedor);
    }


    public List<Producto> getProductsByCategory(String categoryName) {
        return database.query("SELECT nombre,id_vendedor,id_categoria,descripcion,precio,imagen,fecha_añadido, validado FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE nombre = ?)", new ProductMapper(), categoryName);
    }

    public List<Producto> getProductsBySeller(String vendorName) {
        return database.query("SELECT nombre,id_categoria,descripcion,imagen,fecha_añadido, validado, huella_ecologica FROM Producto WHERE id IN(SELECT id_producto FROM Vendedores_Producto WHERE id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ?))", new ProductMapper(), vendorName);
    }

    public boolean isFromOneCategory(String productName, String categoryName) {
    
            return database.queryForObject("SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id_categoria IN(SELECT id FROM Categoria WHERE nombre = ?)",Integer.class, productName, categoryName) == 0;
            
    }

    public void validate(String nombre) throws EmptyResultDataAccessException{
        int exists = database.queryForObject(
                "SELECT COUNT(*) FROM Pendientes_Validacion WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ?",
                Integer.class, nombre);
        if (exists == 0) {
            throw new EmptyResultDataAccessException(1);
        } else {

            database.update(
                    "DELETE FROM Pendientes_Validacion WHERE id_producto IN(SELECT id FROM Producto WHERE nombre = ?)",
                    nombre);
            database.update("UPDATE Producto SET validado = true WHERE nombre = ?", nombre);

        }
    }

    public List<Producto> getProductosPendientesDeValidacion() throws Exception{
        List<Producto> productos = database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado,huella_ecologica FROM Producto WHERE validado = false", new ProductMapper());
        int productosSize = database.queryForObject("SELECT COUNT(*) FROM Pendientes_Validacion", Integer.class);
        if(productosSize == productos.size()){
            return productos;
        }else{
            throw new Exception();
    }
}

    public List<Producto> getProductosComprados(String nickname) throws EmptyResultDataAccessException{
        List<Producto> lista =  database.query("SELECT nombre, id_categoria, id_vendedor, precio, descripcion,imagen,fecha_añadido,validado,huella_ecologica FROM Producto WHERE id IN(SELECT id_producto FROM Detalle_Pedido WHERE id_pedido = ANY(SELECT id FROM Pedido WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)))",new ProductMapper(),nickname);
        if(lista.size() == 0){
            throw new EmptyResultDataAccessException(1);
        }
        return lista;
    }

}
