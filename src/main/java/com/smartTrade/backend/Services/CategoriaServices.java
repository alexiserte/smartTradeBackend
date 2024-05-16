package com.smartTrade.backend.Services;

import com.smartTrade.backend.DAO.CategoriaDAO;
import com.smartTrade.backend.Models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class CategoriaServices {

    @Autowired
    private CategoriaDAO categoriaDAO;

    public void createNewCategory(String nombre, String categoria_principal) {
        categoriaDAO.create(Map.of("nombre",nombre,"categoria_principal",categoria_principal));
    }

    public void deleteCategory(String nombre) {
        categoriaDAO.delete(Map.of("nombre",nombre));
    }

    public void updateCategory(String nombre, Map atributos) {
        categoriaDAO.update(Map.of("nombre",nombre,"atributos",atributos));
    }

    public Categoria readOneCategory(String nombre) {
        return categoriaDAO.readOne(Map.of("nombre",nombre));
    }

    public List<Categoria> readAllCategories() {
        return categoriaDAO.readAll();
    }

    public int getIDFromName(String nombre) {
        List<Categoria> listaDeCategorias = readAllCategories();
        for(Categoria c : listaDeCategorias) {
            if(c.getNombre().equals(nombre)) {
                return listaDeCategorias.indexOf(c) + 1;
            }
        }
        return -1;
    }

    public String getNameFromID(int id) {
        List<Categoria> listaDeCategorias = readAllCategories();
        return listaDeCategorias.get(id - 1).getNombre();
    }

    public List<Categoria> getSubcategories(String nombre) {
        int id = getIDFromName(nombre);
        List<Categoria> listaDeCategorias = readAllCategories();
        List<Categoria> subcategorias = new ArrayList<>();
        for(Categoria c : listaDeCategorias) {
            if(c.getCategoria_principal() == id) {
                subcategorias.add(c);
            }
        }
        return subcategorias;
    }

    public List<Categoria> getCategoriasPrincipales(){
        List<Categoria> listaDeCategorias = readAllCategories();
        List<Categoria> categoriasPrincipales = new ArrayList<>();
        for(Categoria c : listaDeCategorias) {
            if(c.getCategoria_principal() == 0) {
                categoriasPrincipales.add(c);
            }
        }
        return categoriasPrincipales;
    }

    public boolean tieneSubcategorias(String nombre) {
        return !getSubcategories(nombre).isEmpty();
    }
}
