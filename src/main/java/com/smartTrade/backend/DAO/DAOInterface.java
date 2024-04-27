package com.smartTrade.backend.DAO;

import java.util.List;

public interface DAOInterface<T>{
    // Operaciones CRUD a implementar por todos los DAO y cumplir con el patrón de Fábrica Abstracta
    public void create(Object ...args);
    public T readOne(Object ...args);
    public List<? extends T> readAll();
    public void update(Object ...args);
    public void delete(Object ...args);
}
