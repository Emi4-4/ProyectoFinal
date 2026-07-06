package org.example.logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.function.Predicate;
 // podemos reutilizar esta clase para almacenar suministros, alimentos, medicinas
public class Deposito<E> {

    private final ArrayList<E> elementos;

    public Deposito() {
        elementos=new ArrayList<>();
    }

    public void addProducto(E producto) {
        elementos.add(producto);
    }

    public E getProducto(){
        if (elementos.isEmpty()) {
            return null;
        } else {
            return elementos.remove(0);
        }
    }

     public boolean removerElemento(E elemento) {
         return elementos.remove(elemento);
     }

     public E buscarElemento(Predicate<E> condicion) {
         for (E e : elementos) {
             if (condicion.test(e)) {
                 return e;
             }
         }
         return null;
     }

     public List<E> obtenerTodos() {
         return Collections.unmodifiableList(elementos);
     }

     public int getSize() {
         return elementos.size();
     }
}
