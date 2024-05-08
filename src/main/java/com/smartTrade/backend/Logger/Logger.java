package com.smartTrade.backend.Logger;

import org.springframework.cglib.core.Local;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpMethod;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Logger {

    private static Logger logger;
    private PrintWriter writer;
    private enum Level{INFO, ERROR, WARNING, DEBUG, TRACE,SYSTEM,REQUEST,RESPONSE}
    private Logger(){
        try {
            writer = new PrintWriter(new FileWriter("smartTrade.log", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void log(String message){
        String cabecera = createHeader(Level.INFO);
        String messagePrint = "%s %s";
        writer.println(String.format(messagePrint, cabecera, message));
        writer.flush();
    }

    public void logError(Exception e){
        String cabecera = createHeader(Level.ERROR);
        String message = "%s %s: %s";
        writer.println(String.format(message, cabecera, e.getClass().getSimpleName(), e.getLocalizedMessage()));
        String trace = "%s %s";
        cabecera = createHeader(Level.TRACE);
        for (StackTraceElement element : e.getStackTrace()) {
            writer.println(String.format(trace, cabecera, element.toString()));
        }
        writer.flush();
    }

    public void logWarning(String warning){
        String cabecera = createHeader(Level.WARNING);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, warning));
        writer.flush();
    }

    public void logDebug(String debug){
        String cabecera = createHeader(Level.DEBUG);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, debug));
        writer.flush();
    }

    public void logTrace(String trace){
        String cabecera = createHeader(Level.TRACE);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, trace));
        writer.flush();
    }

public void logSystem(String system){
        String cabecera = createHeader(Level.SYSTEM);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, system));
        writer.flush();
    }

    private void logRequest(HttpMethod method, String request){
        String cabecera = createHeader(Level.REQUEST);
        String message = "%s %s %s";
        writer.println(String.format(message,cabecera, method, request));
        writer.flush();
    }

    private void logResponse(String response){
        String cabecera = createHeader(Level.RESPONSE);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, response));
        writer.flush();
    }

    public void logRequestAndResponse(HttpMethod method, String request, String response){
        logRequest(method, request);
        logResponse(response);
    }




    private static String createHeader(Level level){
        String template = "[%s %s %s | %s]";
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("HH:mm") // Formato de 24 horas
                .toFormatter();
        return String.format(template, level.toString(), LocalDate.now(), LocalTime.now().format(timeFormatter), "smartTrade");
    }

    public void close(){
        writer.close();
    }


    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.logRequest(HttpMethod.GET, "/admin/categorias");
    }

}


