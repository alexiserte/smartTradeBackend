package com.smartTrade.backend.models;

public class Comprador {
   
    private int id_comprador;
    private String nickname;
    private String password;
    private String direccion;
    private int puntos_responsabilidad;

    public Comprador(int id_comprador, String nickname, String password, String direccion, int puntos_responsabilidad) {
        this.id_comprador = id_comprador;
        this.nickname = nickname;
        this.password = password;
        this.direccion = direccion;
        this.puntos_responsabilidad = puntos_responsabilidad;
    }

    public Comprador() {}

    public int getId_comprador() {
        return this.id_comprador;
    }

    public void setId_comprador(int id_comprador) {
        this.id_comprador = id_comprador;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getPuntos_responsabilidad() {
        return this.puntos_responsabilidad;
    }

    public void setPuntos_responsabilidad(int puntos_responsabilidad) {
        this.puntos_responsabilidad = puntos_responsabilidad;
    }



}
