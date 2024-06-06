package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.PedidoMapper;
import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.Vendedor;
import com.smartTrade.backend.Services.CountriesServices;
import com.smartTrade.backend.State.EstadosPedido;
import com.smartTrade.backend.Utils.CountriesMethods;
import com.smartTrade.backend.Utils.DateMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PedidoDAO implements DAOInterface<Pedido>{

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    ProductoDAO productoDAO;

    @Autowired
    VendedorDAO vendedorDAO;

    @Autowired
    CountryDAOAndServices countryDAO;

    @Autowired
    CompradorDAO compradorDAO;


    private static JdbcTemplate database;
    public PedidoDAO (JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public void create(Map<String, ?> args) {
        String nickname = (String) args.get("nickname");
        double precio_total = (double) args.get("precio_total");

        // Validación y conversión del tipo de 'productos'
        Object productosObj = args.get("productos");
        if (!(productosObj instanceof Map)) {
            throw new IllegalArgumentException("El argumento 'productos' debe ser un mapa.");
        }

        Map<?, ?> rawProductos = (Map<?, ?>) productosObj;

        // Verificamos que los tipos en el mapa sean correctos
        for (Map.Entry<?, ?> entry : rawProductos.entrySet()) {
            if (!(entry.getKey() instanceof Pair)) {
                throw new IllegalArgumentException("Las claves del mapa 'productos' deben ser del tipo Pair<Producto, String>.");
            }
            if (!(entry.getValue() instanceof Integer)) {
                throw new IllegalArgumentException("Los valores del mapa 'productos' deben ser enteros.");
            }
        }

        // Hacemos el cast seguro ahora que hemos verificado los tipos
        Map<Pair<Producto, String>, Integer> productos = (Map<Pair<Producto, String>, Integer>) rawProductos;

        int id_comprador = usuarioDAO.getID(nickname);
        Date todayDate = DateMethods.getTodayDate();
        final String FIRST_ESTADO = EstadosPedido.PROCESANDO.getNombreEstado();

        // Insertar el pedido en la base de datos
        database.update("INSERT INTO Pedido(id_comprador,fecha_realizacion,estado,precio_total,fecha_llegada) VALUES(?,?,?,?,?)",
                id_comprador, todayDate, FIRST_ESTADO, precio_total, todayDate);

        // Obtener el ID del pedido recientemente insertado
        int id_pedido = database.queryForObject("SELECT id FROM Pedido WHERE id = (SELECT MAX(id) FROM Pedido)", Integer.class);

        List<Date> fechas_entrega = new ArrayList<>();
        for (Pair<Producto, String> parejaProductoVendedor : productos.keySet()) {
            Producto p = parejaProductoVendedor.getFirst();
            String vendedor = parejaProductoVendedor.getSecond();
            int cantidad = productos.get(parejaProductoVendedor);
            int id_producto = productoDAO.getIDFromName(p.getNombre());
            int id_vendedor = usuarioDAO.getID(vendedor);

            String vendorNickname = vendedorDAO.getVendorName(id_vendedor);

            Date fecha_entrega = calculateTimeOfDelivery(vendorNickname, nickname);
            fechas_entrega.add(fecha_entrega);
            database.update("INSERT INTO Detalle_Pedido(id_pedido,id_producto,id_vendedor,cantidad) VALUES(?,?,?,?)", id_pedido, id_producto, id_vendedor, cantidad);
            database.update("UPDATE Vendedores_Producto SET stock_vendedor = stock_vendedor - ? WHERE id_vendedor = ? AND id_producto = ?", cantidad, id_vendedor, id_producto);
        }

        Date latestDate = DateMethods.getLatestDateFromList(fechas_entrega);
        database.update("UPDATE Pedido SET fecha_entrega = ? WHERE id = ?", latestDate, id_pedido);
    }


    @Override
    public Pedido readOne(Map<String, ?> args) {
        int id = (int) args.get("id");
        List<Pair<Integer, Integer>> productos = database.query(
                "SELECT id_producto, cantidad FROM Detalle_Pedido WHERE id_pedido = ?",
                new Object[]{id},
                (rs, rowNum) -> Pair.of(rs.getInt("id_producto"), rs.getInt("cantidad"))
        );

        List<Pedido.ItemPedido> productosMap = new ArrayList<>();

        for (Pair<Integer, Integer> pareja : productos) {
            int id_producto = pareja.getFirst();
            Producto p = productoDAO.getProductByID(id_producto);
            int cantidad = pareja.getSecond();
            int id_vendedor = database.queryForObject("SELECT id_vendedor FROM Detalle_Pedido WHERE id_pedido = ? AND id_producto = ?", Integer.class, id, id_producto);
            String vendorNickname = vendedorDAO.getVendorName(id_vendedor);
            productosMap.add(new Pedido.ItemPedido(p, cantidad, vendorNickname));
        }


        Pedido pedido = database.queryForObject(
                "SELECT * FROM Pedido WHERE id = ?", new PedidoMapper(productosMap), id);

        Date fecha_entrega = database.queryForObject("SELECT fecha_llegada FROM Pedido WHERE id = ?", Date.class, id);
        Date fecha_creacion = database.queryForObject("SELECT fecha_realizacion FROM Pedido WHERE id = ?", Date.class, id);


        int id_estado_del_pedido = pedido.getEstadoActual().getId();
        int id_estado = updateState(fecha_creacion.toLocalDate(), fecha_entrega.toLocalDate()).getId();
        if(id_estado_del_pedido < id_estado) {
            for (int i = id_estado_del_pedido; i < id_estado; i++) {
                boolean op = pedido.siguienteEstado();
                if (!op) {throw new RuntimeException("Error al actualizar el estado del pedido");}
            }
        }
        else{
            for(int i = id_estado_del_pedido; i > id_estado; i--){
                boolean op = pedido.estadoAnterior();
                if (!op) {throw new RuntimeException("Error al actualizar el estado del pedido");}
            }
        }
        updatePedidoState(id, pedido.getEstadoActual());

        pedido = database.queryForObject(
                "SELECT * FROM Pedido WHERE id = ?", new PedidoMapper(productosMap), id);

        Pair<Double,Double> location = localizePedido(pedido);
        pedido.setLocation(location);
        return pedido;
    }

    @Override
    public List<Pedido> readAll() {
        List<Integer> ids = database.queryForList("SELECT id FROM Pedido", Integer.class);
        List<Pedido> pedidos = new ArrayList<>();
        for(Integer id : ids){
            Pedido p = readOne(Map.of("id",id));
            pedidos.add(p);
        }
        return pedidos;
    }

    @Override
    public void update(Map<String, ?> args) {
        int id = (int) args.get("id");
        if(args.containsKey("estado")){
            String estado = (String) args.get("estado");
            if (estado.equals(EstadosPedido.CANCELADO.getNombreEstado())) {
                delete(Map.of("id", id));
            } else {
                updatePedidoState(id, EstadosPedido.valueOf(estado));
            }
        }

    }

    @Override
    public void delete(Map<String, ?> args) {
        int id = (int) args.get("id");
        List<Pair<Integer, Integer>> productos = database.query(
                "SELECT id_producto, cantidad FROM Detalle_Pedido WHERE id_pedido = ?",
                new Object[]{id},
                (rs, rowNum) -> Pair.of(rs.getInt("id_producto"), rs.getInt("cantidad"))
        );
        List<Pedido.ItemPedido> productosMap = new ArrayList<>();
        for (Pair<Integer, Integer> pareja : productos) {
            int id_producto = pareja.getFirst();
            Producto p = productoDAO.getProductByID(id_producto);
            int cantidad = pareja.getSecond();
            int id_vendedor = database.queryForObject("SELECT id_vendedor FROM Detalle_Pedido WHERE id_pedido = ? AND id_producto = ?", Integer.class, id, id_producto);
            String vendorNickname = vendedorDAO.getVendorName(id_vendedor);
            productosMap.add(new Pedido.ItemPedido(p, cantidad, vendorNickname));
        }
        Pedido pedido = database.queryForObject(
                "SELECT * FROM Pedido WHERE id = ?", new PedidoMapper(productosMap), id);

        boolean op = pedido.cancelar();
        if (!op) {throw new RuntimeException("Error al cancelar el pedido");}
        database.update("UPDATE Pedido SET estado = ? WHERE id = ?", pedido.getEstadoActual().getNombreEstado(), id);

    }


    private Date calculateTimeOfDelivery(String vendorNickname, String userNickname){
        String userCountry = database.queryForObject("SELECT pais FROM Usuario WHERE nickname = ?", String.class, userNickname);
        String vendorCountry = database.queryForObject("SELECT pais FROM Usuario WHERE nickname = ?", String.class, vendorNickname);

        if(userCountry.equals(vendorCountry)) {
            return DateMethods.getFutureDate(1);
        }
        else if(CountriesMethods.hasBorderWith(userCountry, vendorCountry)){
            return DateMethods.getFutureDate(2);
        }
        else{
            double distance = countryDAO.getDistanceFromVendorToUser(vendorNickname, userNickname);
            if(distance < 1000){
                return DateMethods.getFutureDate(7);
            }
            else if(distance < 5000){
                return DateMethods.getFutureDate(14);
            }
            else{
                return DateMethods.getFutureDate(28);
            }
        }

    }

    private Pair<Double,Double> localizePedido(Pedido pedido){
        if(pedido.getProductos().size() == 0){
            return countryDAO.getDefaultCoordinates();
        }
        EstadosPedido estado = pedido.getEstadoActual();
        String vendorNickname = pedido.getProductos().get(0).getVendedor();
        String userNickname = usuarioDAO.getUser(pedido.getId_comprador()).getNickname();
        Pair<String,String> location = countryDAO.getCountryAndCityFromUser(userNickname);
        Pair<String,String> locationVendor = countryDAO.getCountryAndCityFromUser(vendorNickname);


        if(estado == EstadosPedido.ESPERANDO_CONFIRMACION || estado == EstadosPedido.PROCESANDO){
            return CountriesMethods.getPointBetweenCities(location.getFirst(),location.getSecond(),locationVendor.getFirst(),locationVendor.getSecond(),0);
        }
        else if(estado == EstadosPedido.ENVIADO){
            return CountriesMethods.getPointBetweenCities(location.getFirst(),location.getSecond(),locationVendor.getFirst(),locationVendor.getSecond(),1);
        }
        else if(estado == EstadosPedido.EN_REPARTO){
            return CountriesMethods.getPointBetweenCities(location.getFirst(),location.getSecond(),locationVendor.getFirst(),locationVendor.getSecond(),2);
        }
        else if(estado == EstadosPedido.ENTREGADO){
            return CountriesMethods.getPointBetweenCities(location.getFirst(),location.getSecond(),locationVendor.getFirst(),locationVendor.getSecond(),3);
        }
        else if(estado == EstadosPedido.CANCELADO){
            return Pair.of(0.0,0.0);
        }

        return null;
    }

    private void updatePedidoState(int id, EstadosPedido estado) {
        database.update("UPDATE Pedido SET estado = ? WHERE id = ?", estado.getNombreEstado(), id);
    }


    private EstadosPedido updateState(LocalDate fecha_creacion, LocalDate fecha_entrega) {
        LocalDate today = LocalDate.now();

        long diasDesdeCreacion = DateMethods.calcularDiferenciaDias(fecha_creacion, today);
        long diasHastaLlegada = DateMethods.calcularDiferenciaDias(today, fecha_entrega);

        if (today.isEqual(fecha_entrega) || today.isAfter(fecha_entrega)) {
            return EstadosPedido.ENTREGADO;
        } else if (diasDesdeCreacion == 0l) {
            return EstadosPedido.ESPERANDO_CONFIRMACION;
        } else if (diasDesdeCreacion == 1l) {
            return EstadosPedido.PROCESANDO;
        } else if (diasDesdeCreacion >= 2l && diasHastaLlegada > 1l) {
            return EstadosPedido.ENVIADO;
        } else if (diasHastaLlegada == 1l) {
            return EstadosPedido.EN_REPARTO;
        } else {
            return EstadosPedido.CANCELADO;
        }
    }


}

