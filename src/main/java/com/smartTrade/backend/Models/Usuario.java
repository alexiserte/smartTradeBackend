package com.smartTrade.backend.Models;

import java.sql.Date;

public class Usuario {
    private String nickname;
    private String password;
    private String direccion;
    private String correo;
    private Date fecha_registro;
    private String country;
    private String city;

    public Usuario(String nickname, String password, String direccion, String correo, String country, String city) {
        this.nickname = nickname;
        this.password = password;
        this.correo = correo;
        this.direccion = direccion;
        this.fecha_registro = Date.valueOf(java.time.LocalDate.now());
        this.country = country;
        this.city = city;
    }

    public Usuario() {
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

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFecha_registro() {
        return this.fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
