package com.smartTrade.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoGuardarMasTarde {
    private int id_producto;
    private int id_vendedor;
    private int id_guardar_mas_tarde;
}
