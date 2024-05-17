package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.PedidoMapper;
import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import com.smartTrade.backend.Models.Vendedor;
import com.smartTrade.backend.State.EstadosPedido;
import com.smartTrade.backend.Utils.DateMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    private JdbcTemplate database;
    public PedidoDAO (JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public void create(Map<String, ?> args) {
        String nickname = (String) args.get("nickname");
        Map<Pair<Producto, String>,Integer> productos = (Map<Pair<Producto, String>,Integer>) args.get("productos");

        int id_comprador = usuarioDAO.getID(nickname);
        Date todayDate = DateMethods.getTodayDate();
        final String FIRST_ESTADO = EstadosPedido.PROCESANDO.getNombreEstado();

        database.update("INSERT INTO Pedido(id_comprador,fecha_realizacion,estado) VALUES(?,?,?)",id_comprador,todayDate,FIRST_ESTADO);

        int id_pedido = database.queryForObject("SELECT * FROM Pedido WHERE id = (SELECT MAX(id) FROM Pedido)",Integer.class);

        for(Pair<Producto, String> parejaProductoVendedor : productos.keySet()){
            Producto p = parejaProductoVendedor.getFirst();
            String vendedor = parejaProductoVendedor.getSecond();
            int cantidad = productos.get(p);
            int id_producto = productoDAO.getIDFromName(p.getNombre());
            int id_vendedor = usuarioDAO.getID(vendedor);

            database.update("INSERT INTO Producto_Pedido(id_pedido,id_producto,id_vendedor,cantidad) VALUES(?,?,?,?)",id_pedido,id_producto,id_vendedor,cantidad);

        }
    }

    @Override
    public Pedido readOne(Map<String, ?> args) {
        int id = (int) args.get("id");
        List<Pair<Integer, Integer>> productos = database.query(
                "SELECT id_producto, cantidad FROM Producto_Pedido WHERE id_pedido = ?",
                new Object[]{id},
                (rs, rowNum) -> Pair.of(rs.getInt("id_producto"), rs.getInt("cantidad"))
        );

        Map<Producto, Integer> productosMap = new HashMap<>();

        for (Pair<Integer, Integer> pareja : productos) {
            int id_producto = pareja.getFirst();
            Producto p = productoDAO.getProductByID(id_producto);
            productosMap.put(p, pareja.getSecond());
        }

        Pedido pedido = database.queryForObject(
                "SELECT * FROM Pedido WHERE id = ?", new PedidoMapper(productosMap), id);

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
    }

