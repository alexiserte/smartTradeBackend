package com.smartTrade.backend.Facada;

import com.smartTrade.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Fachada {

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private CarritoCompraServices carritoCompraServices;

    @Autowired
    private CaracteristicaServices caracteristicaServices;

    @Autowired
    private AdministradorServices administradorServices;

    @Autowired
    private CategoriaServices categoriaServices;

    @Autowired
    private CompradorServices compradorServices;

    @Autowired
    private ListaDeseosServices listaDeseosServices;

    @Autowired
    private SystemServices systemServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private VendedorServices vendedorServices;


    
}
