package org.example.logica;

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

    TipoSuministro(int precio, int efecto) {
        this.precio = precio;
        this.efecto = efecto;
    }
    public int getPrecio() {return  precio;}
    public int getEfecto() {return  efecto;}
}
