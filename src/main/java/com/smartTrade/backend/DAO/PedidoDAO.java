package com.smartTrade.backend.DAO;

import com.smartTrade.backend.Mappers.PedidoMapper;
import com.smartTrade.backend.Mappers.ProductMapper;
import com.smartTrade.backend.Models.Pedido;
import com.smartTrade.backend.Models.Producto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PedidoDAO implements DAOInterface<Pedido>{


    private JdbcTemplate database;

    private List<String> estados = List.of("Esperando confirmación","Procesando","Enviado","En reparto","Recibido");

    public PedidoDAO (JdbcTemplate database) {
        this.database = database;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void create(Map<String,?> args) {
        int id_comprador = (int) args.get("id_comprador");
        List<Producto> productos = (List<Producto>) args.get("productos");
        database.update("INSERT INTO Pedido (id_comprador, estado) VALUES (?,?)", id_comprador);
        int id_pedido = database.queryForObject("SELECT MAX(id) FROM Pedido", Integer.class);
        List<Integer> id_productos = new ArrayList<>();
        /*
        *   ESTO HAY QUE ACABARLO
        *
        *
        */
    }

    @Override
    public Pedido readOne(Map<String,?> args) {
        int id_pedido = (int) args.get("id_pedido");
        List<Producto> productos = database.query("SELECT nombre,descripcion, id_categoria,fecha_añadido, validado, huella_ecologica, id_imagen, stock FROM producto WHERE id IN(SELECT id_producto FROM Detalle_Pedido WHERE id_pedido = ?)", new ProductMapper(), id_pedido);
        return database.queryForObject("SELECT * FROM pedido WHERE id = ?", new PedidoMapper(productos), id_pedido);
    }

    @Override
    public List<? extends Pedido> readAll() {
        List<Pedido> resultado = new ArrayList<>();
        List<Integer> idPedidos = database.queryForList("SELECT id FROM Pedido",Integer.class);
        for(int id : idPedidos){
            resultado.add(readOne(Map.of("id_pedido",id)));
        }
        return resultado;
    }

    @Override
    public void update(Map<String,?> args) {

        /*  ESTO SUPONGO DEBERÁ IR IMPLEMENTADO CON EL PATRÓN ESTADO    */
        System.out.println("No se puede actualizar un pedido");
    }

    @Override
    public void delete(Map<String,?> args) {
        int id_pedido = (int)args.get("id_pedido");
        database.update("DELETE FROM Detalle_Pedido WHERE id_pedido = ?", id_pedido);
        database.update("DELETE FROM Pedido WHERE id = ?", id_pedido);
    }



    public void siguienteEtapaPedido(int id_pedido){
        String estadoActual = database.queryForObject("SELECT estado FROM Pedido WHERE id = ?", String.class, id_pedido);
        int index = estados.indexOf(estadoActual);
        if(index == estados.size()-1){
            System.out.println("El pedido ya ha sido entregado");
        }
        else{
            database.update("UPDATE Pedido SET estado = ? WHERE id = ?", estados.get(index+1), id_pedido);
        }
    }
}
