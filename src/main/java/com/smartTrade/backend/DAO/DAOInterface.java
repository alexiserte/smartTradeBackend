package com.smartTrade.backend.DAO;

import java.util.List;
import java.util.Map;

public interface DAOInterface<T> {
    // Operaciones CRUD a implementar por todos los DAO y cumplir con el patrón de Fábrica Abstracta
    void create(Map<String, ?> args);

    T readOne(Map<String, ?> args);

    List<? extends T> readAll();

    void update(Map<String, ?> args);

    void delete(Map<String, ?> args);
}
