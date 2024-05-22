package com.smartTrade.backend.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateMethods {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static long calcularDiferenciaDias(LocalDate fecha1, LocalDate fecha2) {
        try {
            // Verificar que las fechas están en el formato correcto
            String fecha1Str = fecha1.format(DATE_FORMATTER);
            String fecha2Str = fecha2.format(DATE_FORMATTER);

            // Parsear las fechas de nuevo para asegurarse que están en el formato correcto
            LocalDate fecha1LocalDate = LocalDate.parse(fecha1Str, DATE_FORMATTER);
            LocalDate fecha2LocalDate = LocalDate.parse(fecha2Str, DATE_FORMATTER);

            // Calcular la diferencia de días
            return ChronoUnit.DAYS.between(fecha1LocalDate, fecha2LocalDate);
        } catch (DateTimeParseException e) {
            // Manejar la excepción si las fechas no están en el formato correcto
            throw new IllegalArgumentException("Las fechas deben estar en el formato YYYY-MM-DD", e);
        }
    }

    public static boolean checkIfTodayIsWithinPeriod(Date fecha_inicio, Date fecha_final) throws IllegalArgumentException{
        Date fecha_actual = new Date(System.currentTimeMillis());

        if(fecha_actual.before(fecha_inicio)){
            throw new IllegalArgumentException("El código de descuento aún no es válido");
        }
        else if(fecha_actual.after(fecha_final)){
            throw new IllegalArgumentException("El código de descuento ha expirado");
        }

        return true;
    }

    public static Date getTodayDate(){
        return new Date(System.currentTimeMillis());
    }

    public static Date getTomorrowDate(){
        Date fechaActual = DateMethods.getTodayDate();
        return new Date(fechaActual.getTime() + 86400000);
    }

    public static Date getYesterdayDate(){
        Date fechaActual = DateMethods.getTodayDate();
        return new Date(fechaActual.getTime() - 86400000);
    }

    public static Date getFutureDate(int days){
        Date fechaActual = DateMethods.getTodayDate();
        return new Date(fechaActual.getTime() + days*86400000);
    }

    public static Date getPastDate(int days){
        Date fechaActual = DateMethods.getTodayDate();
        return new Date(fechaActual.getTime() - days*86400000);
    }

    public static boolean isBefore(Date fecha1, Date fecha2){
        return fecha1.before(fecha2);
    }

    public static boolean isAfter(Date fecha1, Date fecha2){
        return fecha1.after(fecha2);
    }


    public static Date getLatestDateFromList(List<Date> dates){
        Date latestDate = dates.get(0);
        for(Date d : dates){
            if(d.after(latestDate)){
                latestDate = d;
            }
        }
        return latestDate;
    }


    public static long getDaysToATime(Date fecha){
        Date fechaActual = DateMethods.getTodayDate();
        return DateMethods.calcularDiferenciaDias(fechaActual.toLocalDate(),fecha.toLocalDate());
    }
}
