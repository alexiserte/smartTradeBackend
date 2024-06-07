package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Models.Vendedor;
import com.smartTrade.backend.Template.Converter;
import com.smartTrade.backend.Template.PNGConverter;
import net.sf.jsqlparser.expression.TryCastExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import com.smartTrade.backend.Factory.ConverterFactory;
import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Utils.*;

@Repository
public class ProductoDAO implements DAOInterface<Object> {

    private static final String PRODUCT_BASE_QUERY = "SELECT nombre, id_categoria, descripcion, id_imagen, fecha_añadido, validado, huella_ecologica, stock, etiqueta_inteligente FROM Producto";
    public static class ProductoAntiguo{
        private String nombre;
        private int id_categoria;
        private String descripcion;
        private String imagen;
        private Date fecha_publicacion;
        private boolean validado;
        private int huella_ecologica;
        private int stock;

        public ProductoAntiguo(Producto p,String imagen) {
            this.nombre = p.getNombre();
            this.id_categoria = p.getId_categoria();
            this.descripcion = p.getDescripcion();
            this.imagen = imagen;
            this.fecha_publicacion = p.getFecha_publicacion();
            this.validado = p.getValidado();
            this.huella_ecologica = p.getHuella_ecologica();
            this.stock = p.getStock();
        }

        public String getNombre() {
            return nombre;
        }

        public int getId_categoria() {
            return id_categoria;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getImagen() {
            return imagen;
        }

        public Date getFecha_publicacion() {
            return fecha_publicacion;
        }

        public boolean isValidado() {
            return validado;
        }

        public int getHuella_ecologica() { return huella_ecologica; }

        public int getStock(){return stock;}

    }
    private JdbcTemplate database;
    @Autowired
    VendedorDAO vendedorDAO;

    @Autowired
    Caracteristica_ProductoDAO CaracteristicaDAO;

    @Autowired
    CategoriaDAO categoriaDAO;

    @Autowired
    SmartTagDAO SmartTagDAO;

    @Autowired
    ImagenDAO imagenDAO;

    public ProductoDAO(JdbcTemplate database) {
        this.database = database;
    }

    public void create(Map<String,?> args) {
        String nombre = (String) args.get("nombre");
        String characteristicName = (String) args.get("characteristicName");
        String vendorName = (String) args.get("vendorName");
        double precio = (double) args.get("precio");
        String descripcion = (String) args.get("descripcion");
        String imagen = (String) args.get("imagen");

        ConverterFactory factory = new ConverterFactory();
        PNGConverter converter = (PNGConverter) factory.createConversor(Converter.getFileFormatFromBase64(imagen));
        String imagenResized = converter.procesar(imagen);

        Random random = new Random();
        int huella_ecologica = random.nextInt(0, 6);
        String smartTag = SmartTagDAO.createSmartTag(nombre);


        try {
            database.queryForObject(PRODUCT_BASE_QUERY + " WHERE nombre = ?", new ProductMapper(), nombre);
            addNewVendorToAnExistingProduct(nombre,vendorName,precio);
        } catch (EmptyResultDataAccessException e) {
            createNewProduct(nombre, descripcion, precio, vendorName, characteristicName, imagenResized, huella_ecologica,smartTag);
        }
    }

    /* Inicio Refactoring Extract Method  */

    private void addNewVendorToAnExistingProduct(String name,String vendorName, double price){
        java.sql.Date fechaSQL = DateMethods.getTodayDate();

        int id_vendedor = vendedorDAO.getVendorID(vendorName);
        int id_producto = getIDFromName(name);

        database.update("INSERT INTO Vendedores_Producto(id_vendedor,id_producto,precio) VALUES(?,?,?)", id_vendedor, id_producto, price);
        database.update("INSERT INTO Historico_Precios(id_producto,precio,fecha_modificacion,id_vendedor) VALUES(?,?,?,?)", id_producto, price, fechaSQL, id_vendedor);
    }

    private void createNewProduct(String nombre, String descripcion, double precio, String vendorName, String categoryName, String imagenResized, int huella_ecologica, String smartTag){
        java.sql.Date fechaSQL = DateMethods.getTodayDate();

        int id_imagen = imagenDAO.getID(imagenResized);
        if (id_imagen == -1) {
            imagenDAO.create(Map.of("imagen",imagenResized));
            id_imagen = imagenDAO.getID(imagenResized);
        }

        int id_categoria = categoriaDAO.getIDFromName(categoryName);
        int id_vendedor = vendedorDAO.getVendorID(vendorName);

        database.update("INSERT INTO Producto(nombre, id_categoria, descripcion,id_imagen,fecha_añadido,validado,huella_ecologica,etiqueta_inteligente) VALUES (?, ?,?,?,?,?,?,?);", nombre, id_categoria, descripcion, id_imagen, fechaSQL, false, huella_ecologica,smartTag);
        database.update("INSERT INTO Pendientes_Validacion(id_producto) SELECT id FROM Producto WHERE nombre = ?;", nombre);

        int id_producto = getIDFromName(nombre);

        database.update("INSERT INTO Historico_Precios(id_producto,precio,fecha_modificacion,id_vendedor) VALUES(?,?,?,?)", id_producto, precio, fechaSQL, id_vendedor);
        database.update("INSERT INTO Vendedores_Producto(id_vendedor,id_producto,precio) VALUES(?,?,?)", id_vendedor, id_producto, precio);
    }

    /* Fin Refactoring Extract Method  */

    public List<Object> readOne(Map<String,?> args) {
        String productName = (String) args.get("productName");

        List<Object> res = new ArrayList<>();

        int id_producto = getIDFromName(productName);
        Producto producto = getProductByID(id_producto);
        String categoria = categoriaDAO.getNameFromID(producto.getId_categoria());
        HashMap<String, String> caracteristicas = CaracteristicaDAO.getSmartTag(productName);

        res.add(0, producto);
        res.add(1, caracteristicas);
        res.add(2, categoria);

        List<Integer> vendedoresID = database.queryForList("SELECT id_vendedor FROM Vendedores_Producto WHERE id_producto = ? ORDER BY id", Integer.class, id_producto);

        List<String> vendedores = new ArrayList<>();
        for (Integer integer : vendedoresID) {
            Vendedor vendedor = vendedorDAO.getVendedorWithID(integer);
            vendedores.add(vendedor.getNickname());
        }

        List<Double> precios = database.queryForList("SELECT precio FROM Vendedores_Producto WHERE id_producto = ? ORDER BY id", Double.class, id_producto);

        Map<String, Double> mapaDeVendedores = new HashMap<>();
        for (int i = 0; i < vendedores.size(); i++) {
            mapaDeVendedores.put(vendedores.get(i), precios.get(i));
        }

        res.add(3, mapaDeVendedores);

        return res;
    }

    public Producto readOneProduct(String productName) {
        return database.queryForObject(PRODUCT_BASE_QUERY + " WHERE nombre = ?", new ProductMapper(), productName);
    }

    public ProductoAntiguo readOneProductAntiguo(String productName) {
        int id_imagen = database.queryForObject("SELECT id_imagen FROM Producto WHERE nombre = ?", Integer.class,
                productName);
        String imagen = imagenDAO.readOne(Map.of("id_imagen",id_imagen));
        return new ProductoAntiguo(readOneProduct(productName), imagen);
    }

    public List<Producto> readAll() {
        return database.query(PRODUCT_BASE_QUERY, new ProductMapper());
    }


    @SuppressWarnings("unchecked")
    public void update(Map<String,?> args) {

        HashMap<String, ?> atributos = (HashMap<String, ?>) args.get("atributos");
        String nombre = (String) atributos.get("nombre");
        String imagen = (String) atributos.get("imagen");
        System.out.println(imagen);
        String categoryName = (String) atributos.get("categoria");

        Integer id_imagen = -1;
        id_imagen = imagenDAO.getID(imagen);
        try {
            System.out.println("perro sanxe");
            if (id_imagen == -1 || id_imagen == null) {
                Map<String,String> args2 = new HashMap<>();
                args2.put("imagen",imagen);
                System.out.println("Esto se ejecuta4");
                imagenDAO.create(args2);
                id_imagen = imagenDAO.getID(imagen);
            }
            else{
                System.out.println("Esto se ejecuta3");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            throw new RuntimeException("\nsombra aqui sombra alla maquillate maquillate\n");
        }

        int id_categoria = categoriaDAO.getIDFromName(categoryName);

        System.out.println("en la categoria no dio error");
        if(atributos.keySet().contains("precio") || atributos.keySet().contains("stock")){
            updateProductFromOneVendor(nombre,(String) atributos.get("vendedor"),atributos);
            return;
        }
        System.out.println("actualizarlo deberia no dar error");

        System.out.println("fffff");

        List<String> keys = new ArrayList<>(atributos.keySet());
        Producto product = null;
        try {
            product = readOneProduct(nombre);
        }catch (Exception e){
            throw new RuntimeException("Perú es español");
        }
        System.out.println("Esto se ejecuta4");


        for (String key : keys) {
            Object valor = atributos.get(key);
            if (valor instanceof Integer) {
                database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ? AND ",
                        (Integer) valor, nombre);
            } else if (valor instanceof String) {
                database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ?",
                        (String) valor, nombre);
            } else if (valor instanceof Double) {
                database.update("UPDATE Producto SET " + key + " = ? WHERE nombre = ?",
                        (Double) valor, nombre);
            }
        }

        System.out.println("Esto se ejecuta7");
    }

    public void delete(Map<String,?> args) {
        String nombre = (String) args.get("nombre");
        int id_producto = getIDFromName(nombre);
        database.update("DELETE FROM Producto WHERE nombre = ?", nombre);
        database.update("DELETE FROM Historico_Precios WHERE id_producto = ?", id_producto);
    }


    public Producto getProductByID(int id) {
        return database.queryForObject(PRODUCT_BASE_QUERY + " WHERE id = ?", new ProductMapper(), id);
    }

    public int getIDFromName(String name) {
        return database.queryForObject("SELECT id FROM Producto WHERE nombre = ?", Integer.class, name);
    }


    public void validate(String nombre) throws EmptyResultDataAccessException {
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

    public List<Producto> getProductosComprados(String nickname) throws EmptyResultDataAccessException {
        List<Producto> lista = database.query(
                PRODUCT_BASE_QUERY + " WHERE id IN(SELECT id_producto FROM Detalle_Pedido WHERE id_pedido = ANY(SELECT id FROM Pedido WHERE id_comprador = ANY(SELECT id FROM Usuario WHERE nickname = ?)))",
                new ProductMapper(), nickname);
        if (lista.size() == 0) {
            throw new EmptyResultDataAccessException(1);
        }
        return lista;
    }

    public void deleteProduct(String productName, String vendorName) {
        int id_producto = getIDFromName(productName);
        int id_vendedor = vendedorDAO.getVendorID(vendorName);
        database.update("DELETE FROM Historico_Precios WHERE id_producto = ? AND id_vendedor = ?", id_producto,
                id_vendedor);
        database.update("DELETE FROM Vendedores_Producto WHERE id_producto = ? AND id_vendedor = ?", id_producto,
                id_vendedor);
    }

    public void updateProductFromOneVendor(String nombre, String vendorName, Map atributos) {
        int id_producto = 0;
        int id_vendedor = 0;
        List<String> keys = new ArrayList<>();
        try {
            keys = new ArrayList<>(atributos.keySet());
            id_producto = getIDFromName(nombre);
            id_vendedor = vendedorDAO.getVendorID(vendorName);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Perú es español");
        }

        System.out.println("ESTO ESTA BIEN X1");

        for(String key : keys) {

            if (key.equals("precio")) {
                double precio = (double) atributos.get("precio");
                java.sql.Date fechaActual = DateMethods.getTodayDate();
                database.update(
                        "INSERT INTO Historico_Precios(id_producto,precio,fecha_modificacion,id_vendedor) VALUES(?,?,?,?)",
                        id_producto, precio, fechaActual, id_vendedor);
                System.out.println("ESTO ESTA BIEN X2");
                database.update("UPDATE Vendedores_Producto SET precio = ? WHERE id_producto = ? AND id_vendedor = ?",
                        precio, id_producto, id_vendedor);
                System.out.println("ESTO ESTA BIEN X3");
            } else if (keys.contains("stock")) {
                int stock = (int) atributos.get("stock");
                System.out.println("ESTO ESTA BIEN X4");
                database.update("UPDATE Vendedores_Producto SET stock_vendedor = ? WHERE id_producto = ? AND id_vendedor = ?",
                        stock, id_producto, id_vendedor);
                System.out.println("ESTO ESTA BIEN X5");
            }
        }
    }


    public int getStockFromOneProduct(String productName,String vendorName){
        int id_producto = getIDFromName(productName);
        int id_vendedor = vendedorDAO.getVendorID(vendorName);
        return database.queryForObject("SELECT stock_vendedor FROM Vendedores_Producto WHERE id_producto = ? AND id_vendedor = ?", Integer.class,id_producto,id_vendedor);
    }

    public void addValoracion(int id_pedido, String productName, int valoracion){
        int id_producto = getIDFromName(productName);
        database.update("UPDATE Detalle_Pedido SET valoracion = ? WHERE id_producto = ? AND id_pedido = ?",
                valoracion, id_producto, id_pedido);
    }

    public double getValoracion(String productName){
        int id_producto = getIDFromName(productName);
        return database.queryForObject("SELECT valoracion_media FROM Producto WHERE id = ?", Integer.class,id_producto);
    }


}
