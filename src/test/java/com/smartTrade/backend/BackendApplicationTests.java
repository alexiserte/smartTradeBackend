package com.smartTrade.backend;

import com.smartTrade.backend.ControllerMethods.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    private final BasicTest basicTest = new BasicTest();
    private final AdministradorTest administradorTest = new AdministradorTest();
    private final CompradorTest compradorTest = new CompradorTest();
    private final VendedorTest vendedorTest = new VendedorTest();
    private final ProductoTest productoTest = new ProductoTest();

    @Test
    void contextLoads() {
    }

    @Test
    void runBasicTest() {
        basicTest.serverIsRunning();
        basicTest.welcomeMessage();
        basicTest.teamMembers();
    }

    @Test
    void runAdministradorTest() {
        administradorTest.AdminAllTest();
    }

    @Test
    void runCompradorTest() {
        compradorTest.fullCompradorTest();
    }

    @Test
    void runVendedorTest() {
        vendedorTest.fullVendedorTest();
    }

    @Test
    void runProductoTest() {
        productoTest.fullProductoTest();
    }


}
