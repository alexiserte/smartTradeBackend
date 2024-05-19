package com.smartTrade.backend.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.Map;

public class DAOMethods {

    private static JdbcTemplate database;
    public DAOMethods (JdbcTemplate database) {
        this.database = database;
    }

    public static void create(String table, Map<String, ?> args) {
        String query = "INSERT INTO " + table + "(";
        for (String key : args.keySet()) {
            query += key + ",";
        }
        query = query.substring(0, query.length() - 1);
        query += ") VALUES(";
        for (String key : args.keySet()) {
            query += args.get(key) + ",";
        }
        query = query.substring(0, query.length() - 1);
        query += ")";
        database.update(query);
    }

    public static void update(String table, Map<String, ?> args, String condition) {
        String query = "UPDATE " + table + " SET ";
        for (String key : args.keySet()) {
            query += key + "=" + args.get(key) + ",";
        }
        query = query.substring(0, query.length() - 1);
        if(condition != null && !condition.isEmpty()) {query += " WHERE " + condition;}
        database.update(query);
    }


    public static void delete(String table, String condition) {
        String query = "DELETE FROM " + table;
        if(condition != null && !condition.isEmpty()) {query += " WHERE " + condition;}
        database.update(query);
    }


    public static void read(String table,String attributes, String condition, RowCallbackHandler rch) {
        String attributesQuery = attributes;
        if(attributes == null || attributes.isEmpty()) {attributesQuery = "*";}
          String query = "SELECT " + attributesQuery + " FROM " + table;
        if(condition != null && !condition.isEmpty()) {query += " WHERE " + condition;}
        database.query(query, rch);
    }
}
