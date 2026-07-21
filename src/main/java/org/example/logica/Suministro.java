package org.example.logica;
/**
 * Representa un suministro físico dentro de la tienda (como alimentos,
 * shampoo o medicinas), basado en un tipo específico de suministro.
 *
 * @author Emiliano
 */
public class Suministro {
    TipoSuministro tipo;
    /**
     * Constructor para crear un nuevo suministro según su categoría.
     *
     * @param tipo El tipo de suministro que define sus características y efectos
     */
    public Suministro(TipoSuministro tipo) {
        this.tipo = tipo;
    }

    /**
     * Constructor para crear un nuevo suministro según su categoría.
     *
     */

    public TipoSuministro getTipo() {
        return tipo;
    }

}
