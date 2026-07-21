package org.example.logica;
/**
 * Enumeración que representa las distintas categorías de suministros consumibles
 * que se pueden comprar en la tienda (alimentos, medicinas y shampoo),
 * junto con sus respectivos precios y valores de efecto.
 *
 * @author Emiliano
 * @author Lenin
 */
public enum TipoSuministro {
    //SuminsitroEspecifico(precioDeCompra, puntosDeEfecto):
    // (los valores pueden ser modificados mas adelante )
    ALIMENTO_PERRO(1500, 30),
    ALIMENTO_GATO(1200, 25),
    ALIMENTO_PEZ(500, 15),
    ALIMENTO_PAJARO(800, 20),
    MEDICINA(5000, 40),
    SHAMPOO(2000, 60);

    private final int precio;
    private final int efecto;

    /**
     * Constructor para inicializar el costo y el impacto en los atributos de la mascota.
     *
     * @param precio Costo de compra del suministro
     * @param efecto Valor numérico del beneficio que otorga sobre la mascota
     */
    TipoSuministro(int precio, int efecto) {
        this.precio = precio;
        this.efecto = efecto;
    }
    public int getPrecio() {return  precio;}
    public int getEfecto() {return  efecto;}
}
