package com.smartTrade.backend.models;
import java.sql.Date;
public class Administrador extends Usuario {

    public Administrador(String nickname, String password, String direccion, String correo) {
        super(nickname, password, direccion, correo);
    }

    public Administrador() {}

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
    
}
