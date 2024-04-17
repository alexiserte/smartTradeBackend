package com.smartTrade.backend;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smartTrade.backend.factory.ProductFactory;
import com.smartTrade.backend.models.Alimentacion;
import com.smartTrade.backend.models.Bebida;
import com.smartTrade.backend.models.Comida;
import com.smartTrade.backend.models.Deporte;
import com.smartTrade.backend.models.Electronica;
import com.smartTrade.backend.models.Frescos;
import com.smartTrade.backend.models.Higiene;
import com.smartTrade.backend.models.Moda;
import com.smartTrade.backend.models.Procesados;
import com.smartTrade.backend.models.Product_Types;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

        System.out.println("\n-----------------PRODUCT FACTORY TEST-----------------\n");
        ProductFactory productFactory = new ProductFactory();
        
        Moda moda = (Moda) productFactory.getProduct(Product_Types.MODA, "Camiseta", 1, 10.0, "Camiseta de algodon", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of("M", "Nike", "Blanco", "Camiseta", "Hombre"));
        System.out.println("La variable moda es: " + moda);
        System.out.println("La variable moda es: " + moda.getNombre());
        System.out.println("La variable moda es de tipo: " + moda.getClass().getName());
        Higiene higiene = (Higiene) productFactory.getProduct(Product_Types.HIGIENE, "Jabon", 1, 10.0, "Jabon de manos", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of());
        System.out.println("La variable higiene es: " + higiene);
        System.out.println("La variable higiene es: " + higiene.getNombre());
        System.out.println("La variable higiene es de tipo: " + higiene.getClass().getName());
        Alimentacion alimentacion = (Alimentacion) productFactory.getProduct(Product_Types.ALIMENTACION, "Alimento", 1, 10.0, "Alimento para perros", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of());
        System.out.println("La variable alimentacion es: " + alimentacion);
        System.out.println("La variable alimentacion es: " + alimentacion.getNombre());
        System.out.println("La variable alimentacion es de tipo: " + alimentacion.getClass().getName());
        Comida comida = (Comida) productFactory.getProduct(Product_Types.COMIDA, "Comida", 1, 10.0, "Comida para perros", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of("Comida", 10.0));
        System.out.println("La variable comida es: " + comida);
        System.out.println("La variable comida es: " + comida.getNombre());
        System.out.println("La variable comida es de tipo: " + comida.getClass().getName());
        Procesados procesados = (Procesados) productFactory.getProduct(Product_Types.PROCESADOS, "Procesado", 1, 10.0, "Procesado para perros", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of("Procesado", 10.0));
        System.out.println("La variable procesados es: " + procesados);
        System.out.println("La variable procesados es: " + procesados.getNombre());
        System.out.println("La variable procesados es de tipo: " + procesados.getClass().getName());
        Frescos frescos = (Frescos) productFactory.getProduct(Product_Types.FRESCOS, "Fresco", 1, 10.0, "Fresco para perros", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of("Fresco", 10.0));
        System.out.println("La variable frescos es: " + frescos);
        System.out.println("La variable frescos es: " + frescos.getNombre());
        System.out.println("La variable frescos es de tipo: " + frescos.getClass().getName());
        Bebida bebida = (Bebida) productFactory.getProduct(Product_Types.BEBIDA, "Bebida", 1, 10.0, "Bebida para perros", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of());
        System.out.println("La variable bebida es: " + bebida);
        System.out.println("La variable bebida es: " + bebida.getNombre());
        System.out.println("La variable bebida es de tipo: " + bebida.getClass().getName());
        Deporte deporte = (Deporte) productFactory.getProduct(Product_Types.DEPORTE, "Balon", 1, 10.0, "Balon de futbol", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of("Balon", "Nike", "Blanco", "Futbol"));
        System.out.println("La variable deporte es: " + deporte);
        System.out.println("La variable deporte es: " + deporte.getNombre());
        System.out.println("La variable deporte es de tipo: " + deporte.getClass().getName());
        Electronica electronica = (Electronica) productFactory.getProduct(Product_Types.ELECTRONICA, "Movil", 1, 10.0, "Movil de ultima generacion", 1, "imagen", new java.sql.Date(2021, 1, 1), true, 1, List.of("Movil", "Samsung", "Blanco", "Movil"));
        System.out.println("La variable electronica es: " + electronica);
        System.out.println("La variable electronica es: " + electronica.getNombre());
        System.out.println("La variable electronica es de tipo: " + electronica.getClass().getName());
        System.out.println("\n-----------------PRODUCT FACTORY TEST-----------------\n");

    }

}
