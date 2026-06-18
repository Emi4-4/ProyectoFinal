package org.example;

import java.util.ArrayList;

 // podemos reutilizar esta clase para almacenar suministros, alimentos, medicinas
public class Deposito<E> {
    private final ArrayList<E> Elemento;
    public Deposito() {
        Elemento=new ArrayList<>();
    }
    public void addProducto(E producto) {
        Elemento.add(producto);
    }
    public E getProducto(){
        if (Elemento.isEmpty()) {
            return null;
        } else {
            return Elemento.remove(0);
        }
    }
}
