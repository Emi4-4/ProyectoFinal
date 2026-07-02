package org.example.logica;

import java.util.ArrayList;
import java.util.function.Predicate;
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

     public E buscarElemento(Predicate<E> condicion) {
         for (E e : Elemento) {
             if (condicion.test(e)) {
                 return e;
             }
         }
         return null;
     }
}
