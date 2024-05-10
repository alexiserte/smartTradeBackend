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
import java.util.Random;

public class Logger {

    private static Logger logger;
    private PrintWriter writer;
    private static Random random = new Random();
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
        int id = generateID();
        String cabecera = createHeader(Level.INFO, id);
        String messagePrint = "%s %s";
        writer.println(String.format(messagePrint, cabecera, message));
        writer.flush();
    }

    public void logError(Exception e){
        int id = generateID();
        String cabecera = createHeader(Level.ERROR, id);
        String message = "%s %s: %s";
        writer.println(String.format(message, cabecera, e.getClass().getSimpleName(), e.getLocalizedMessage()));
        String trace = "%s %s";
        cabecera = createHeader(Level.TRACE, id);
        for (StackTraceElement element : e.getStackTrace()) {
            writer.println(String.format(trace, cabecera, element.toString()));
        }
        writer.flush();
    }

    public void logWarning(String warning){
        int id = generateID();
        String cabecera = createHeader(Level.WARNING, id);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, warning));
        writer.flush();
    }

    public void logDebug(String debug){
        int id = generateID();
        String cabecera = createHeader(Level.DEBUG, id);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, debug));
        writer.flush();
    }

    private void logTrace(String trace, int id){
        String cabecera = createHeader(Level.TRACE,id);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, trace));
        writer.flush();
    }

public void logSystem(String system){
        int id = generateID();
        String cabecera = createHeader(Level.SYSTEM,id);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, system));
        writer.flush();
    }

    private void logRequest(HttpMethod method, String request,int id){
        String cabecera = createHeader(Level.REQUEST,id);
        String message = "%s %s %s";
        writer.println(String.format(message,cabecera, method, request));
        writer.flush();
    }

    private void logResponse(String response,int id){
        String cabecera = createHeader(Level.RESPONSE,id);
        String message = "%s %s";
        writer.println(String.format(message,cabecera, response));
        writer.flush();
    }

    public void logRequestAndResponse(HttpMethod method, String request, String response){
        int id = generateID();
        logRequest(method, request,id);
        logResponse(response,id);
    }




    private static String createHeader(Level level, int id){
        String template = "[%s %s %s | %d | %s]";
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("HH:mm") // Formato de 24 horas
                .toFormatter();
        return String.format(template, level.toString(), LocalDate.now(), LocalTime.now().format(timeFormatter),id, "smartTrade");
    }

    private static int generateID(){
        return random.nextInt(10000);
    }

    public void close(){
        writer.close();
    }

    public static String getFullLog(){
        String log = "";
        try {
            log = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("smartTrade.log")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }

    public static String getLog(int id){
        String log = getFullLog();
        String[] lines = log.split("\n");
        StringBuilder res = new StringBuilder();
        for (String line : lines) {
            if(line.contains(" | " + id + " | ")){
                res.append(line + "\n");
            }
        }
        return res.toString();

    }

}


