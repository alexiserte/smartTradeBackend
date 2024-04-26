package com.smartTrade.backend.DAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import com.smartTrade.backend.utils.DateMethods;
import com.smartTrade.backend.utils.StringTemplates;

@Repository
public class PrecioDAO {

    private final JdbcTemplate database;

    public PrecioDAO(JdbcTemplate database) {
        this.database = database;
    }

    public TreeMap<String, Object> getStats(String productName) {
        TreeMap<String, Object> stats = new TreeMap<>();
        int id_product = database.queryForObject(
                "SELECT id FROM Producto WHERE nombre = ?", Integer.class, productName);
        double preciominimo = database.queryForObject(
                "SELECT MIN(precio) FROM Historico_Precios WHERE id_producto = ?", Double.class, id_product);
        double preciomaximo = database.queryForObject(
                "SELECT MAX(precio) FROM Historico_Precios WHERE id_producto = ?", Double.class, id_product);
        double preciomedio = database.queryForObject(
                "SELECT AVG(precio) FROM Historico_Precios WHERE id_producto = ?", Double.class, id_product);

        List<String> vendedores = database.queryForList(
                "SELECT nickname FROM Usuario WHERE id IN (SELECT id_vendedor FROM Vendedores_Producto WHERE id_producto IN (SELECT id FROM Producto WHERE nombre = ?))",
                String.class, productName);

        List<LocalDate> fechas = database.queryForList(
                "SELECT fecha_modificacion FROM Historico_Precios WHERE id_producto = ANY(SELECT id FROM Producto WHERE nombre = ?) ORDER BY id DESC",
                LocalDate.class, productName);
        LocalDate fechaActual = fechas.get(fechas.size() - 1);

        for (int j = 0; j < vendedores.size(); j++) {
            List<Double> preciosFromOneProduct = database.queryForList(
                    "SELECT precio FROM Historico_Precios WHERE id_producto = ? AND id_vendedor IN (SELECT id FROM Usuario WHERE nickname = ?) ORDER BY id",
                    Double.class, id_product, vendedores.get(j));

            TreeMap<String, Object> preciosVendedor = new TreeMap<>();
            preciosVendedor.put("Vendedor", vendedores.get(j));

            for (int i = 0; i < preciosFromOneProduct.size(); i++) {
                preciosVendedor.put("Precio " + (i + 1), preciosFromOneProduct.get(i));
            }
            double precioActual = preciosFromOneProduct.get(preciosFromOneProduct.size() - 1);

            if (preciosFromOneProduct.size() <= 1) {
                preciosVendedor.put("Dato", StringTemplates.PRECIO_NORMAL);
            } else {
                if (precioActual <= preciominimo) {
                    preciosVendedor.put("Dato", String.format(StringTemplates.PRECIO_MINIMO, productName));
                } else if (precioActual >= preciomaximo) {
                    preciosVendedor.put("Dato", String.format(StringTemplates.PRECIO_MAXIMO, productName));
                } else if (isPrecioDisminuido(preciosFromOneProduct, precioActual)) {
                    long diferenciaDias = DateMethods.calcularDiferenciaDias(LocalDate.now(), fechaActual);
                    if (diferenciaDias < 1) {
                        preciosVendedor.put("Dato", StringTemplates.PRECIO_NOVEDAD);
                    } else {
                        preciosVendedor.put("Dato", String.format(StringTemplates.PRECIO_DISMINUIDO, diferenciaDias));
                    }
                } else {
                    long diferenciaDias = DateMethods.calcularDiferenciaDias(fechaActual, LocalDate.now());
                    if (diferenciaDias <= 7) {
                        preciosVendedor.put("Dato", StringTemplates.PRECIO_AUMENTADO);
                    } else {
                        preciosVendedor.put("Dato", StringTemplates.PRECIO_NORMAL);
                    }
                }
            }

            preciosVendedor.put("Precio mínimo", preciominimo);
            preciosVendedor.put("Precio máximo", preciomaximo);
            preciosVendedor.put("Precio promedio", preciomedio);
            preciosVendedor.put("Número de cambios", preciosFromOneProduct.size());

            stats.put("Vendedor " + (j + 1), preciosVendedor);
        }

        // Estadísticas generales
        stats.put("Precio máximo general", preciomaximo);
        stats.put("Precio mínimo general", preciominimo);
        stats.put("Precio promedio general", preciomedio);
        return stats;
    }

    private boolean isPrecioDisminuido(List<Double> precios, double precioActual) {
        if (precios.size() < 2) {
            return false;
        }
        double precioAnterior = precios.get(precios.size() - 2);
        return precioAnterior > precioActual;
    }
}
