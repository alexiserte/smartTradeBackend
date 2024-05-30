package com.smartTrade.backend.UseCasesTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartTrade.backend.Logger.Logger;
import com.smartTrade.backend.smartTradeConexion;
import org.junit.jupiter.api.Test;

public class BuyAProduct {

    private final Logger logger = Logger.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();
    private final smartTradeConexion conexion = new smartTradeConexion();

    @Test
    void buyAProductTest(){
        assert true;
    }
}
