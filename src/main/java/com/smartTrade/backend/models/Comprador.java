package com.smartTrade.backend.models;

public class Comprador {
   
    private String nickname;
    private String password;
    private String direccion;
    private int puntos_responsabilidad;
    private String correo;

    public Comprador(String nickname, String password, String direccion, int puntos_responsabilidad, String correo) {
        this.nickname = nickname;
        this.password = password;
        this.correo = correo;
        this.direccion = direccion;
        this.puntos_responsabilidad = puntos_responsabilidad;
    }

    public Comprador() {}

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

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }



}
