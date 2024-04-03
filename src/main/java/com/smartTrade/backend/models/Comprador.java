package com.smartTrade.backend.models;

import java.sql.Date;
public class Comprador extends Usuario{
   
    private int puntosResponsabilidad;

    public Comprador(String nickname, String password, String direccion, String correo,int puntosResponsabilidad) {

        super(nickname, password, direccion, correo);
        this.puntosResponsabilidad = puntosResponsabilidad;
    }

    public Comprador() {}

    public int getpuntosResponsabilidad() {
        return this.puntosResponsabilidad;
    }

    public void setpuntosResponsabilidad(int puntosResponsabilidad) {
        this.puntosResponsabilidad = puntosResponsabilidad;
    }

    
    public String getNickname() {
        return super.getNickname();
    }

    public void setNickname(String nickname) {
        super.setNickname(nickname);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public String getDireccion() {
        return super.getDireccion();
    }

    public void setDireccion(String direccion) {
        super.setDireccion(direccion);
    }

    public String getCorreo() {
        return super.getCorreo();
    }

    public void setCorreo(String correo) {
        super.setCorreo(correo);
    }

    public Date getFecha_registro() {
        return super.getFecha_registro();
    }
    
    public void setFecha_registro(Date fecha_registro) {
        super.setFecha_registro(fecha_registro);
    }


}
