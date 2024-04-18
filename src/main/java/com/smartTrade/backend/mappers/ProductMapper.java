package com.smartTrade.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.smartTrade.backend.models.Product_Types;
import com.smartTrade.backend.models.Producto;
import com.smartTrade.backend.daos.Caracteristica_ProductoDAO;
import com.smartTrade.backend.daos.VendedorDAO;
import com.smartTrade.backend.factory.ProductFactory;

public class ProductMapper implements RowMapper<Producto> {

    @Autowired
    Caracteristica_ProductoDAO caracteristicaProductoDAO;
    @Autowired
    VendedorDAO vendedorDAO;
    @Override
    public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id_vendedor = (rs.getInt("id_vendedor"));
        int id_categoria = (rs.getInt("id_categoria"));
        String descripcion = (rs.getString("descripcion"));
        double precio = (rs.getDouble("precio"));
        String nombre = (rs.getString("nombre"));
        String image = (rs.getString("imagen"));
        java.sql.Date fecha = (rs.getDate("fecha_añadido"));
        boolean validado = (rs.getBoolean("validado"));
        int huella_ecologica = (rs.getInt("huella_ecologica"));
        Product_Types tipoDeProducto = Product_Types.fromID(rs.getInt("id_categoria"));
        HashMap<String,String> atributos = caracteristicaProductoDAO.getSmartTag(rs.getString("nombre"), vendedorDAO.getVendorName(rs.getInt("id_vendedor")));
        List<Object> listaDeParametros = new ArrayList<>();
        for (String key : atributos.keySet()) {
            listaDeParametros.add(key);
            listaDeParametros.add((Object) atributos.get(key));
        }
        Producto producto = ProductFactory.getProduct(tipoDeProducto, nombre, id_vendedor, precio, descripcion, id_categoria, image, fecha, validado, huella_ecologica, listaDeParametros);
        return producto;
    }
}