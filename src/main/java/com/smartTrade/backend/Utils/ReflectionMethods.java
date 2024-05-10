package com.smartTrade.backend.Utils;

public class ReflectionMethods {
    public static String obtenerNombreMetodoActual() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 3) {
            StackTraceElement elemento = stackTrace[2];
            return elemento.getMethodName();
        } else {
            return "Desconocido";
        }
    }
}
