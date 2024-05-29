package com.smartTrade.backend.Facade;

import com.smartTrade.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Fachada {

    @Autowired
    public ProductoServices productoServices;

    @Autowired
    public CarritoCompraServices carritoCompraServices;

    @Autowired
    public CaracteristicaServices caracteristicaServices;

    @Autowired
    public AdministradorServices administradorServices;

    @Autowired
    public CategoriaServices categoriaServices;

    @Autowired
    public CompradorServices compradorServices;

    @Autowired
    public ListaDeseosServices listaDeseosServices;

    @Autowired
    public SystemServices systemServices;

    @Autowired
    public UsuarioServices usuarioServices;

    @Autowired
    public VendedorServices vendedorServices;

    @Autowired
    public CountriesServices countriesServices;

    @Autowired
    public GuardarMasTardeServices guardarMasTardeServices;

    
}
