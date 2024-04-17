package com.smartTrade.backend.factory;

import com.smartTrade.backend.models.Administrador;
import com.smartTrade.backend.models.Usuario;
import com.smartTrade.backend.models.Vendedor;
import com.smartTrade.backend.models.Comprador;
import com.smartTrade.backend.models.User_Types;

public class UserFactory {
    
    public Usuario createUser(User_Types type, String nickname, String password, String direccion, String correo){
        switch(type){
            case ADMINISTRADOR:
                return new Administrador(nickname, password, direccion, correo);
            case VENDEDOR:
                return new Vendedor(nickname, password, direccion, correo);
            case COMPRADOR:
                return new Comprador(nickname, password, direccion, correo, 0);
            default:
                return null;
        }
    }

}
