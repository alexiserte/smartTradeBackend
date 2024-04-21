package com.smartTrade.backend.utils;

import java.time.LocalDate;

public class DateMethods {
    public static long calcularDiferenciaDias(LocalDate fecha2, LocalDate fecha1) {
        return java.time.temporal.ChronoUnit.DAYS.between(fecha1, fecha2);
    }
}
