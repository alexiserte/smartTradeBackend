package com.smartTrade.backend.Fachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartTrade.backend.DAO.*;

@Component
public class Fachada {
    
    @Autowired
    public CategoriaDAO categoriaDAO;

    @Autowired
    public AdministradorDAO adminDAO;

    @Autowired
    public CompradorDAO compradorDAO;

    @Autowired
    public VendedorDAO vendedorDAO;

    @Autowired
    public ProductoDAO productoDAO;

    @Autowired
    public CaracteristicaDAO caracteristicaDAO;

    @Autowired
    public Carrito_CompraDAO carritoCompraDAO;

    @Autowired
    public GuardarMasTardeDAO guardarMasTardeDAO;

    @Autowired
    public ImagenDAO imagenDAO;

    @Autowired
    public ListaDeDeseosDAO listaDeDeseosDAO;

    @Autowired
    public PrecioDAO precioDAO;

    @Autowired
    public UsuarioDAO usuarioDAO;

    @Autowired
    public Caracteristica_ProductoDAO caracteristicaProductoDAO;

    @Autowired
    public SmartTagDAO smartTagDAO;


    
}
