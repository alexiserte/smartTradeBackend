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
        Map<Pair<Producto, String>,Integer> productos = (Map<Pair<Producto, String>,Integer>) args.get("productos");

        int id_comprador = usuarioDAO.getID(nickname);
        Date todayDate = DateMethods.getTodayDate();
        final String FIRST_ESTADO = EstadosPedido.PROCESANDO.getNombreEstado();

        database.update("INSERT INTO Pedido(id_comprador,fecha_realizacion,estado,precio_total) VALUES(?,?,?)",id_comprador,todayDate,FIRST_ESTADO,precio_total);
        int id_pedido = database.queryForObject("SELECT * FROM Pedido WHERE id = (SELECT MAX(id) FROM Pedido)",Integer.class);
        List<Date> fechas_entrega = new ArrayList<>();
        for(Pair<Producto, String> parejaProductoVendedor : productos.keySet()){
            Producto p = parejaProductoVendedor.getFirst();
            String vendedor = parejaProductoVendedor.getSecond();
            int cantidad = productos.get(p);
            int id_producto = productoDAO.getIDFromName(p.getNombre());
            int id_vendedor = usuarioDAO.getID(vendedor);

            String vendorNickname = vendedorDAO.getVendorName(id_vendedor);

            Date fecha_entrega = calculateTimeOfDelivery(vendorNickname, nickname);
            fechas_entrega.add(fecha_entrega);
            database.update("INSERT INTO Detalle_Pedido(id_pedido,id_producto,id_vendedor,cantidad) VALUES(?,?,?,?)",id_pedido,id_producto,id_vendedor,cantidad);
        }

        Date latestDate = DateMethods.getLatestDateFromList(fechas_entrega);
        database.update("UPDATE Pedido SET fecha_entrega = ? WHERE id = ?",latestDate,id_pedido);
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
    }

    @Override
    public void delete(Map<String, ?> args) {

    }

    private void updatePedidoState(Map<String, ?> args){
        int id = (int) args.get("id");
        EstadosPedido estado = (EstadosPedido) args.get("estado");
        database.update("UPDATE Pedido SET estado = ? WHERE id = ?",estado.getNombreEstado(),id);
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

        return null;
    }


    public void updateActualStates() {
        List<Integer> ids = database.queryForList("SELECT id FROM Pedido", Integer.class);
        List<Pedido> pedidos = new ArrayList<>();
        for (Integer id : ids) {
            Pedido p = readOne(Map.of("id", id));
            pedidos.add(p);
        }
        for (Pedido p : pedidos) {
            EstadosPedido estadoRecomendado = recommendedState(p);
            EstadosPedido estadoActual = p.getEstadoActual();
            if (estadoRecomendado != estadoActual) {
                updatePedidoState(Map.of("id", p.getId(), "estado", estadoRecomendado));
                p.siguienteEstado();
            }
        }
    }

    private EstadosPedido recommendedState(Pedido pedido) {
        LocalDate fecha_realizacion = pedido.getFecha_realizacion().toLocalDate();
        LocalDate fecha_entrega = pedido.getFecha_entrega().toLocalDate();

        LocalDate today = LocalDate.now();

        long daysSinceCreation = ChronoUnit.DAYS.between(fecha_realizacion, today);
        long daysLeftToDelivery = ChronoUnit.DAYS.between(today, fecha_entrega);

        if (daysLeftToDelivery <= 0) {
            return EstadosPedido.ENTREGADO;
        } else if (daysLeftToDelivery == 1) {
            return EstadosPedido.EN_REPARTO;
        } else if (daysSinceCreation == 2) {
            return EstadosPedido.ENVIADO;
        } else if (daysSinceCreation == 1) {
            return EstadosPedido.PROCESANDO;
        }
        return EstadosPedido.ESPERANDO_CONFIRMACION;
    }




}

