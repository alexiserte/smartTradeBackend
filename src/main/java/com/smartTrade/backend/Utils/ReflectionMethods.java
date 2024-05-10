package com.smartTrade.backend.Utils;

public class ReflectionMethods {
    public static String obtenerNombreMetodoActual() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 3) {
            StackTraceElement elemento = stackTrace[2]; // El índice 2 es el método que llamó a obtenerNombreMetodoActual()
            return elemento.getMethodName();
        } else {
            return "Desconocido";
        }
    }
}
