package com.smartTrade.backend.models;

public class Comprador {
   
    private int id_carrito;
    private String nickname;
    private String password;
    private String direccion;
    private int puntos_responsabilidad;

    public Comprador(int id_carrito, String nickname, String password, String direccion, int puntos_responsabilidad) {
        this.id_carrito = id_carrito;
        this.nickname = nickname;
        this.password = password;
        this.direccion = direccion;
        this.puntos_responsabilidad = puntos_responsabilidad;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getPuntos_responsabilidad() {
        return puntos_responsabilidad;
    }

    public void setPuntos_responsabilidad(int puntos_responsabilidad) {
        this.puntos_responsabilidad = puntos_responsabilidad;
    }

}
