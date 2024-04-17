package com.smartTrade.backend.models;

public enum Product_Types {
        ALIMENTACION(1),
        BEBIDA(9),
        COMIDA(8),
        DEPORTE(7),
        ELECTRONICA(3),
        FRESCOS(5),
        HIGIENE(4),
        MODA(2),
        PROCESADOS(8);

        private final int id;

        Product_Types(int id) {
          this.id = id;
        }

        public int getID() {
            return id;
        }
}
