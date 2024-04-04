package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.Usuario;
import com.smartTrade.backend.models.Vendedor;
import com.smartTrade.backend.models.Comprador;

public class UserFactory {
    
    public Usuario createVoidUser(String type){
        switch(type){
            case "Administrador":
                return new Administrador();
            case "Vendedor":
                return new Vendedor();
            case "Comprador":
                return new Comprador();
            default:
                return null;
        }
    }

    public Usuario createUser(String type, String nickname, String password, String direccion, String correo){
        switch(type){
            case "Administrador":
                return new Administrador(nickname, password, direccion, correo);
            case "Vendedor":
                return new Vendedor(nickname, password, direccion, correo);
            case "Comprador":
                return new Comprador(nickname, password, direccion, correo, 0);
            default:
                return null;
        }
    }

}
