package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.PedidoMapper;
import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PedidoDAO implements DAOInterface<Pedido>{

    private JdbcTemplate database;

    public PedidoDAO (JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public void create(Object... args) {

    }

    @Override
    public Pedido readOne(Object... args) {
        int id_pedido = (int) args[0];
        List<Producto> productos = database.query("SELECT nombre,descripcion, id_categoria,fecha_añadido, validado, huella_ecologica, id_imagen FROM producto WHERE id IN(SELECT id_producto FROM Detalle_Pedido WHERE id_pedido = ?)", new ProductMapper(), id_pedido);
        return database.queryForObject("SELECT * FROM pedido WHERE id = ?", new PedidoMapper(productos), id_pedido);
    }

    @Override
    public List<? extends Pedido> readAll() {
        return List.of();
    }

    @Override
    public void update(Object... args) {

    }

    @Override
    public void delete(Object... args) {

    }
}
