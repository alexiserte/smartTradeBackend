package com.smartTrade.backend.Utils;

import java.sql.Date;
import java.time.LocalDate;

public class DateMethods {

    public static long calcularDiferenciaDias(LocalDate fecha1, LocalDate fecha2) {
        return java.time.temporal.ChronoUnit.DAYS.between(fecha1, fecha2);
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
        Date fechaActual = DateMethods.getTodayDate();
        return new Date(fechaActual.getTime());
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
}
