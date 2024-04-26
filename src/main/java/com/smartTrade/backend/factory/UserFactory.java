package com.smartTrade.backend.Factory;

import com.smartTrade.backend.Models.Administrador;
import com.smartTrade.backend.Models.Comprador;
import com.smartTrade.backend.Models.User_Types;
import com.smartTrade.backend.Models.Usuario;
import com.smartTrade.backend.Models.Vendedor;

public class UserFactory {
    
    public static Usuario createUser(User_Types type, String nickname, String password, String direccion, String correo){
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
