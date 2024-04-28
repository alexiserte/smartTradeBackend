package com.smartTrade.backend.Utils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SmartTag {

    private static final String SMART_TAG = "Origen: %s\nProducto: %s\nProceso de producción: %s\nImpacto: %s\nODS: %s\n";

    public static void main(String[] args) {
        try {
            System.out.println("\n-----------------------------------------------\n");
            System.out.println(createSmartTag("Producto"));
        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Field> getFields() throws ClassNotFoundException {
        Field[] listaDeAtributos;
        listaDeAtributos = Class.forName("SmartTagStrings").getDeclaredFields();
        List<Field> listaDeAtributosList = new ArrayList<>();
        for (Field field : listaDeAtributos) {
            listaDeAtributosList.add(field);
        }
        return listaDeAtributosList;
    }

    public static String getRandomMensaje(List<Field> mensajes) throws IllegalAccessException, ClassNotFoundException {
        int random = new Random().nextInt(mensajes.size());
        Field field = mensajes.get(random);
        field.setAccessible(true);
        return (String) field.get(Class.forName("SmartTagStrings"));
    }

    public static String getRandomMensajeProduccion() throws IllegalAccessException, ClassNotFoundException {
        List<Field> listaDeAtributos = getFields();
        List<Field> mensajesProduccion = listaDeAtributos.stream()
                .filter(s -> s.getName().startsWith("MENSAJE_PRODUCCION"))
                .collect(Collectors.toList());
        return getRandomMensaje(mensajesProduccion);
    }

    public static String getRandomMensajeImpacto() throws IllegalAccessException, ClassNotFoundException {
        List<Field> listaDeAtributos = getFields();
        List<Field> mensajesImpacto = listaDeAtributos.stream()
                .filter(s -> s.getName().startsWith("MENSAJE_IMPACTO"))
                .collect(Collectors.toList());
        return getRandomMensaje(mensajesImpacto);
    }

    public static String getRandomMensajeOds() throws IllegalAccessException, ClassNotFoundException {
        List<Field> listaDeAtributos = getFields();
        List<Field> mensajesOds = listaDeAtributos.stream()
                .filter(s -> s.getName().startsWith("MENSAJE_ODS"))
                .collect(Collectors.toList());
        return getRandomMensaje(mensajesOds);
    }

    public static String createSmartTag(String producto) throws IllegalAccessException, ClassNotFoundException{
        String pais = CountriesMethods.getRandomCountry();
        String produccion = String.format(getRandomMensajeProduccion(), producto, pais);
        String impacto = String.format(getRandomMensajeImpacto(), producto);
        String ods = String.format(getRandomMensajeOds(), producto);
        return String.format(SMART_TAG, pais, producto, produccion, impacto, ods);
    }

    
}
